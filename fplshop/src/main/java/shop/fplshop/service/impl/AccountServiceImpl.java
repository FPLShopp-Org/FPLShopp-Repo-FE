package shop.fplshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.fplshop.entity.Account;
import shop.fplshop.repository.AccountRepository;
import shop.fplshop.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account findByUsername(String username) {
       return accountRepository.findByUsername(username);
    }

    
}
