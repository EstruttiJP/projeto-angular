package me.register.springboot.service;

import me.register.springboot.domain.Account;
import me.register.springboot.dto.AccountDTO;
import me.register.springboot.map.AccountMapper;
import me.register.springboot.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    public Account findById(Long id){
        return accountRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Account save(AccountDTO accountDTO){
        return accountRepository.save(AccountMapper.INSTANCE.toAccount(accountDTO));
    }


}
