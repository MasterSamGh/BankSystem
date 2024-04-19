package com.mysite.banking.mapper;

import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.dto.LegalClientDto;
import com.mysite.banking.dto.RealClientDto;
import com.mysite.banking.model.Client;
import com.mysite.banking.model.LegalClient;
import com.mysite.banking.model.RealClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface ClientMapstruct {
    default ClientDto mapToClientDto(Client client){
        if (client instanceof RealClient){
            return mapToRealClientDto((RealClient) client);
        }else {
            return mapToLegalClientDto((LegalClient) client);
        }
    }
    RealClientDto mapToRealClientDto(RealClient realClient);
    LegalClientDto mapToLegalClientDto(LegalClient legalClient);
    List<ClientDto> mapToClientDtoList(List<Client> clientList);

    default Client mapToClient(ClientDto clientDto,Client client){
        if (clientDto instanceof RealClientDto){
            return mapToRealClient((RealClientDto)clientDto,(RealClient) client);
        }else {
            return mapToLegalClient((LegalClientDto) clientDto,(LegalClient) client);
        }
    }

     default Client mapToClient(ClientDto clientDto){
        if (clientDto instanceof RealClientDto){
            return mapToRealClient((RealClientDto)clientDto,new RealClient(null,null));
        }else {
            return mapToLegalClient((LegalClientDto) clientDto,new LegalClient(null,null));
        }
    }
    @Mapping(ignore = true,target = "id")
    LegalClient mapToLegalClient(LegalClientDto legalClientDto ,@MappingTarget LegalClient legalClient);
    @Mapping(ignore = true,target = "id")
    RealClient mapToRealClient(RealClientDto realClientDto,@MappingTarget RealClient realClient);
}
