package com.mysite.banking.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysite.banking.model.ClientType;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class ClientDto {
    private Integer id;
    private String name;
    private String number;
    @JsonIgnore
    private String password;
    private String email;
    private final ClientType type;

}
