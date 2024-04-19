package com.mysite.banking.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mysite.banking.model.ClientType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class RealClientDto extends  ClientDto{
    private String family;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date birthday;
    public RealClientDto() {
        super(ClientType.REAL);
    }

    public RealClientDto(Integer id, String name, String number,String password,String email) {
        super(id, name, number, password,email,ClientType.REAL);
    }
}
