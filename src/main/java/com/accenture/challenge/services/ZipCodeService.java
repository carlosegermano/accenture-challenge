package com.accenture.challenge.services;

import com.accenture.challenge.model.Address;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ZipCodeService {

    @Value("${url.api.cep}")
    private String URL_CEP;

    public Address getAddressByZipCode(String zipCode) {
        RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        Object object = restTemplate.getForObject(this.URL_CEP.concat("/").concat(zipCode), Object.class);

        if (this.isZipCodeValid(object)) {
            Address address = new ObjectMapper().convertValue(object, Address.class);
            return address;
        } else {
            throw new IllegalArgumentException("ZipCode invalid!");
        }
    }

    public boolean isZipCodeValid(Object address) {
        return address != null && !(address instanceof ArrayList<?>);
    }
}
