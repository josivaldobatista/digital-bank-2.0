package com.jfb.account.mapper;

import com.jfb.account.dto.AccountResponse;
import com.jfb.account.dto.BalanceResponse;
import com.jfb.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getCustomerId(),
                account.getCustomerName(),
                account.getStatus(),
                account.getBalance(),
                account.getAvailableBalance(),
                account.getCreatedAt()
        );
    }

    public BalanceResponse toBalanceResponse(Account account) {
        return new BalanceResponse(
                account.getId(),
                account.getBalance(),
                account.getBlockedAmount(),
                account.getAvailableBalance()
        );
    }
}
