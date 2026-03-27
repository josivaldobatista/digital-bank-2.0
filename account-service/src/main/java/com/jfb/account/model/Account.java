package com.jfb.account.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts")
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

    @Column(name = "account_number", nullable = false, unique = true, length = 30)
    private String accountNumber;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "customer_name", length = 150)
    private String customerName;

    @Column(name = "customer_cpf", length = 11)
    private String customerCpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountType accountType = AccountType.CHECKING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(nullable = false, precision = 19, scale = 4)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "blocked_amount", nullable = false, precision = 19, scale = 4)
    @Builder.Default
    private BigDecimal blockedAmount = BigDecimal.ZERO;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // Métodos de domínio (Rich Domain Model) - comportamento da conta
    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        if (getAvailableBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para saque");
        }
        this.balance = this.balance.subtract(amount);
    }

    public BigDecimal getAvailableBalance() {
        return balance.subtract(blockedAmount);
    }

    public void blockAmount(BigDecimal amount) {
        validateAmount(amount);
        if (getAvailableBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo disponível insuficiente para bloquear");
        }
        this.blockedAmount = this.blockedAmount.add(amount);
    }

    public void unblockAmount(BigDecimal amount) {
        validateAmount(amount);
        if (blockedAmount.compareTo(amount) < 0) {
            throw new IllegalStateException("Valor bloqueado insuficiente");
        }
        this.blockedAmount = this.blockedAmount.subtract(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
    }
}