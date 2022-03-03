package com.dvivasva.account.utils;

import com.dvivasva.account.dto.AccountDto;
import com.dvivasva.account.model.Account;
import org.springframework.beans.BeanUtils;

public final class AccountUtil {
    private AccountUtil() {
    }

    public static AccountDto entityToDto(final Account account) {
        var accountDto = new AccountDto();
        BeanUtils.copyProperties(account, accountDto);
        return accountDto;
    }
    public static Account dtoToEntity(final AccountDto accountDto) {
        var entity = new Account();
        BeanUtils.copyProperties(accountDto, entity);
        return entity;
    }


}
