package shop.fplshop.service;

import shop.fplshop.entity.Account;

public interface AccountService {

    Account findByUsername(String username);
    
}
