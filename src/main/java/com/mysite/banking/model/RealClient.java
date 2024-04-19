package com.mysite.banking.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "real_client")
@Setter
@Getter
@ToString(callSuper = true)
public class RealClient extends Client implements Serializable {
    private String family;
    private Date birthday;

    public RealClient() {
        super(ClientType.REAL);
    }
    public RealClient(String name, String number) {
        super(name, number, ClientType.REAL);
    }
    @Override
    public boolean equals(Object obj){
        return obj instanceof  RealClient &&
                ((RealClient)obj).getName().equals(getName()) &&
                ((RealClient)obj).getFamily().equals(getFamily());
    }
}
