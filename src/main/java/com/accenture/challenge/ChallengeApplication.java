package com.accenture.challenge;

import com.accenture.challenge.enums.Person;
import com.accenture.challenge.model.Address;
import com.accenture.challenge.model.Company;
import com.accenture.challenge.model.Supplier;
import com.accenture.challenge.repositories.CompanyRepository;
import com.accenture.challenge.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class ChallengeApplication implements CommandLineRunner {

	private final CompanyRepository companyRepository;
	private final SupplierRepository supplierRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Company company1 = Company.builder()
				.cnpj("90052795000174")
				.tradeName("AlfaCompany")
				.zipCode("58649575")
				.address(Address.builder()
						.cep("58297000")
						.uf("PB")
						.cidade("Rio Tinto")
						.bairro("Centro")
						.logradouro("Rua do Sol")
						.build()
				)
				.build();
		Company company2 = Company.builder()
				.cnpj("98411506000198")
				.tradeName("BetaCompany")
				.zipCode("56985452")
				.build();
		Company company3 = Company.builder()
				.cnpj("75646563000150")
				.tradeName("GamaCompany")
				.zipCode("65987458")
				.build();

		this.companyRepository.saveAll(Arrays.asList(company1, company2, company3));

		Supplier supplier1 = Supplier.builder()
				.nationalDocument("42917397020")
				.personType(Person.NATURAL_PERSON)
				.name("John Doe")
				.email("johndoe@gmail.com")
				.zipCode("58974521")
				.nationalId("2456854")
				.birthday(new SimpleDateFormat("dd/MM/yyyy").parse("02/10/1980"))
				.build();

		Supplier supplier2 = Supplier.builder()
				.nationalDocument("02253913000145")
				.personType(Person.LEGAL_PERSON)
				.name("John Doe")
				.email("johndoe@gmail.com")
				.zipCode("58974521")
				.build();

		Supplier supplier3 = Supplier.builder()
				.nationalDocument("74351124000158")
				.personType(Person.LEGAL_PERSON)
				.name("Jane Doe")
				.email("janedoe@gmail.com")
				.zipCode("75845122")
				.build();

		this.supplierRepository.saveAll(Arrays.asList(supplier1, supplier2, supplier3));

		company1.getSuppliers().addAll(Arrays.asList(supplier1, supplier2));
		company2.getSuppliers().addAll(Arrays.asList(supplier2, supplier3));
		company3.getSuppliers().addAll(Arrays.asList(supplier1, supplier2, supplier3));

		supplier1.getCompanies().addAll(Arrays.asList(company1, company3));
		supplier2.getCompanies().addAll(Arrays.asList(company1, company2, company3));
		supplier3.getCompanies().addAll(Arrays.asList(company2, company3));

		this.companyRepository.saveAll(Arrays.asList(company1, company2, company3));
		this.supplierRepository.saveAll(Arrays.asList(supplier1, supplier2, supplier3));
	}
}
