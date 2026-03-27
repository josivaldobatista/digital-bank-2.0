-- V01__Create_Accounts_Table.sql
CREATE TABLE accounts (
                          id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          account_number      VARCHAR(30) UNIQUE NOT NULL,
                          customer_id         UUID NOT NULL,
                          customer_name       VARCHAR(150),
                          customer_cpf        VARCHAR(11),

                          account_type        VARCHAR(20)  NOT NULL DEFAULT 'CHECKING',
                          status              VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',

    -- Saldo financeiro - padrão moderno
                          balance             NUMERIC(19,4) NOT NULL DEFAULT 0.0000,     -- 4 casas decimais para precisão monetária
                          blocked_amount      NUMERIC(19,4) NOT NULL DEFAULT 0.0000,

                          created_at          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT chk_account_type CHECK (account_type IN ('CHECKING', 'SAVINGS')),
                          CONSTRAINT chk_status      CHECK (status IN ('ACTIVE', 'INACTIVE', 'BLOCKED', 'CLOSED')),
                          CONSTRAINT chk_balance_non_negative     CHECK (balance >= 0),
                          CONSTRAINT chk_blocked_non_negative     CHECK (blocked_amount >= 0)
);

CREATE INDEX idx_accounts_customer_id ON accounts(customer_id);
CREATE INDEX idx_accounts_account_number ON accounts(account_number);
CREATE INDEX idx_accounts_status ON accounts(status);