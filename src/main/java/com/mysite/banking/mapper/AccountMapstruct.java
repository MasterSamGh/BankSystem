package com.mysite.banking.mapper;

import com.mysite.banking.dto.*;
import com.mysite.banking.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface AccountMapstruct {

    AccountDto mapToAccountDto(Account account);
    List<AccountDto> mapToAccountDtoList(List<Account> accountList);
    Amount mapToAmount(AmountDto amountDto);
    @Mapping(ignore = true,target = "id")
    Account mapToAccount(AccountDto accountDto ,@MappingTarget Account account);
    @Mapping(ignore = true,target = "id")
    Account mapToAccount(AccountDto accountDto);

}
