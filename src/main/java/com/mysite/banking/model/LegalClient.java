package com.mysite.banking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "legal_client")
@Setter
@Getter
@ToString(callSuper = true)
public class LegalClient extends Client implements Serializable {
    private String fax;


    public LegalClient() {
        super(ClientType.LEGAL);
    }
    public LegalClient(String name, String number) {
        super(name, number, ClientType.LEGAL);
    }
    @Override
    public boolean equals(Object obj){
        return obj instanceof LegalClient &&
                ((LegalClient)obj).getName().equals(getName());
    }
}
