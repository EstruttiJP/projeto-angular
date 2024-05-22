package me.register.springboot.map;

import me.register.springboot.domain.Account;
import me.register.springboot.dto.AccountDTO;

public enum AccountMapper {
    INSTANCE;
    public Account toAccount(AccountDTO accountDTO){
        if (accountDTO==null){
            return null;
        }else{
            Account account = new Account();
            account.setEmail(accountDTO.getEmail());
            account.setName(accountDTO.getName());
            account.setPassword(accountDTO.getPassword());
            return account;
        }
    }
}
