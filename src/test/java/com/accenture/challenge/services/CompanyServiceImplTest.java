package com.accenture.challenge.services;

import com.accenture.challenge.builders.AddressBuilder;
import com.accenture.challenge.builders.CompanyBuilder;
import com.accenture.challenge.builders.CompanyCreationDTOBuilder;
import com.accenture.challenge.exceptions.DataIntegrityValidationException;
import com.accenture.challenge.exceptions.IllegalArgumentException;
import com.accenture.challenge.exceptions.ObjectNotFoundException;
import com.accenture.challenge.exceptions.ZipCodeInvalidException;
import com.accenture.challenge.model.Company;
import com.accenture.challenge.model.CompanyCreationDTO;
import com.accenture.challenge.repositories.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@Tag("service")
@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    private CompanyService companyService;

    @Mock
    private ZipCodeService zipCodeService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(this.zipCodeService, "URL_CEP", "http://cep.la/");
        this.companyService = new CompanyServiceImpl(this.companyRepository, this.zipCodeService);
    }

    @Test
    @DisplayName("Add a company when successful")
    public void shouldSaveACompanyWhenSuccessful() {
        CompanyCreationDTO companyCreationDTO = CompanyCreationDTOBuilder.createCompanyCreationDTO();
        Company company = CompanyBuilder.createCompany();
        when(this.zipCodeService.getAddressByZipCode(anyString())).thenReturn(AddressBuilder.createAddress());
        when(this.companyRepository.save(any(Company.class))).thenReturn(company);

        Company saved = this.companyService.save(companyCreationDTO);

        Assertions.assertAll("result",
                () -> assertThat(saved.getCnpj(), is(companyCreationDTO.getCnpj())),
                () -> assertThat(saved.getTradeName(), is(companyCreationDTO.getTradeName())),
                () -> assertThat(saved.getZipCode(), is(companyCreationDTO.getZipCode())),
                () -> assertThat(saved.getAddress().getCep(), is(companyCreationDTO.getAddress().getCep())),
                () -> assertThat(saved.getSuppliers().size(), is(companyCreationDTO.getSuppliers().size()))
        );
    }

    @Test
    @DisplayName("Throws an exception when Zip Code is invalid")
    public void shouldntThrowsAnExceptionWhenZipcodeIsInvalid() {
        when(this.zipCodeService.getAddressByZipCode(anyString())).thenThrow(ZipCodeInvalidException.class);
        CompanyCreationDTO companyCreationDTO = CompanyCreationDTOBuilder.createCompanyCreationDTO();


        Assertions.assertThrows(ZipCodeInvalidException.class, () -> this.companyService.save(companyCreationDTO));
        verify(this.companyRepository, times(0)).save(any(Company.class));
    }

    @Test
    @DisplayName("Throws an exception when there are duplicated fields")
    public void shouldThrowsADataIntegrityValidationException() {

        when(this.zipCodeService.getAddressByZipCode(anyString())).thenReturn(AddressBuilder.createAddress());
        CompanyCreationDTO companyCreationDTO = CompanyCreationDTOBuilder.createCompanyCreationDTO();
        doThrow(DataIntegrityValidationException.class).when(this.companyRepository).save(any(Company.class));
        Assertions.assertThrows(DataIntegrityValidationException.class, () -> this.companyService.save(companyCreationDTO));
        verify(this.companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    @DisplayName("Throws an exception when the supplier is underage")
    public void shouldThrowsAnIllegalArgumentException() {
        CompanyCreationDTO companyCreationDTO = CompanyCreationDTOBuilder.createCompanyCreationDTOWithUnderageSupplier();


        when(this.zipCodeService.getAddressByZipCode(anyString())).thenReturn(AddressBuilder.createAddressOfParana());

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.companyService.save(companyCreationDTO));
        verify(this.companyRepository, times(0)).save(any(Company.class));
    }

    @Test
    @DisplayName("Return a company with such id")
    public void shouldReturnACompanyWhenSuccessful() {
        Company company = CompanyBuilder.createCompany();
        when(this.companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        Company response = this.companyService.findById(anyLong());

        Assertions.assertAll("result",
                () -> assertThat(response.getCnpj(), is(company.getCnpj())),
                () -> assertThat(response.getTradeName(), is(company.getTradeName())),
                () -> assertThat(response.getZipCode(), is(company.getZipCode())),
                () -> assertThat(response.getAddress().getCep(), is(company.getAddress().getCep())),
                () -> assertThat(response.getSuppliers().size(), is(company.getSuppliers().size()))
        );
    }

    @Test
    @DisplayName("Throws an exception when there is no company")
    public void shouldThrowsAnObjectNotFoundException() {
        when(this.companyRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> this.companyService.findById(anyLong()));
        verify(this.companyRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Return a list of company")
    public void shouldReturnAListOfCompanyWhenSuccessful() {
        when(this.companyRepository.findAll()).thenReturn(CompanyBuilder.createListOfCompany());

        List<Company> response = this.companyService.findAll();

        assertThat(response.size(), is(2));
        verify(this.companyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update a company when successful")
    public void shouldUpdateACompanyWhenSuccessful() {
        Company company = CompanyBuilder.createCompany();
        company.setId(1L);
        when(this.companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(this.zipCodeService.getAddressByZipCode(anyString())).thenReturn(AddressBuilder.createAddress2());
        when(this.companyRepository.save(any(Company.class))).thenReturn(company);

        company.setZipCode("58280000");
        company.setTradeName("Trade Name");

        Company updated = this.companyService.update(company, 1L);

        Assertions.assertAll("result",
                () -> assertThat(updated.getCnpj(), is("32069325000117")),
                () -> assertThat(updated.getTradeName(), is("Trade Name")),
                () -> assertThat(updated.getZipCode(), is("58280000")),
                () -> assertThat(updated.getAddress().getUf(), is("PB")),
                () -> assertThat(updated.getAddress().getCidade(), is("Mamanguape"))
        );
    }

    @Test
    @DisplayName("Remove a company")
    public void shouldRemoveACompanyWhenSuccessful() {
        when(this.companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyBuilder.createCompany()));
        doNothing().when(this.companyRepository).deleteById(anyLong());
        this.companyService.delete(anyLong());
        verify(this.companyRepository, times(1)).deleteById(anyLong());
    }
}