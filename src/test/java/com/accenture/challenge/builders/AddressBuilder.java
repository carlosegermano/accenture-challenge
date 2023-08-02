package com.accenture.challenge.builders;

import com.accenture.challenge.model.Address;

public class AddressBuilder {

    public static Address createAddress() {
        return Address.builder()
                .cep("58297000")
                .cidade("Rio Tinto")
                .bairro("Centro")
                .uf("PB")
                .logradouro("Rua do Sol")
                .aux("AP 1001")
                .build();
    }

    public static Address createAddressOfParana() {
        return Address.builder()
                .cep("54256542")
                .cidade("Nome Cidade")
                .bairro("Centro")
                .uf("PR")
                .logradouro("Rua do Sol")
                .aux("AP 1001")
                .build();
    }

    public static Address createAddress2() {
        return Address.builder()
                .cep("58280000")
                .cidade("Mamanguape")
                .bairro("Centro")
                .uf("PB")
                .logradouro("Rua Sem Fim")
                .aux("")
                .build();
    }
}
