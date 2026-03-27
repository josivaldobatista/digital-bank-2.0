package com.jfb.account.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "idx_account_number", columnList = "account_number", unique = true),
        @Index(name = "idx_account_customer", columnList = "customer_id")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType = AccountType.CHECKING;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_cpf")
    private String customerCpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "blocked_amount", precision = 19, scale = 2)
    private BigDecimal blockedAmount = BigDecimal.ZERO;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        if (getAvailableBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }

    public BigDecimal getAvailableBalance() {
        return balance.subtract(blockedAmount);
    }

    public void blockAmount(BigDecimal amount) {
        validateAmount(amount);
        if (getAvailableBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient available balance");
        }
        this.blockedAmount = this.blockedAmount.add(amount);
    }

    public void unblockAmount(BigDecimal amount) {
        validateAmount(amount);
        if (blockedAmount.compareTo(amount) < 0) {
            throw new IllegalStateException("Blocked amount insufficient");
        }
        this.blockedAmount = this.blockedAmount.subtract(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
}
