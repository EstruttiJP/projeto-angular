package me.register.springboot.controller;

import me.register.springboot.domain.Account;
import me.register.springboot.dto.AccountDTO;
import me.register.springboot.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAll(){
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findById(@PathVariable Long id){
        return new ResponseEntity<>(accountService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> save(@RequestBody @Valid AccountDTO accountDTO){
        return new ResponseEntity<>(accountService.save(accountDTO), HttpStatus.CREATED);
    }

}
