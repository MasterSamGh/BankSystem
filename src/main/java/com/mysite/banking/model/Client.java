package com.mysite.banking.model;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "client")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LegalClient.class,name = "LEGAL"),
        @JsonSubTypes.Type(value = RealClient.class,name = "REAL")
})
public abstract class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "client_sequence")
    @SequenceGenerator(name = "client_sequence", sequenceName = "hibernate_client_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String number;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private final ClientType type;
    private Boolean deleted;
    @Version
    private Long version;

    public Client(ClientType type){
        this.type = type;
        this.deleted = false;
    }


    public Client(String name, String number, ClientType type) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.deleted = false;
    }




    private String capitalizeFirstCharacter(String str){
        if(str != null && !str.isEmpty()){
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
        return str;
    }
}