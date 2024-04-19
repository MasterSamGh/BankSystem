package com.mysite.banking.dto;

import com.mysite.banking.model.ClientType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
public class LegalClientDto extends ClientDto{
    private String fax;

    public LegalClientDto() {
        super(ClientType.LEGAL);
    }

    public LegalClientDto(Integer id, String name, String number,String password,String email) {
        super(id, name, number, password,email,ClientType.LEGAL);
    }
}
