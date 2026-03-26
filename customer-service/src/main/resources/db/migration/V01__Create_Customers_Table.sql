-- V01__Create_Customers_Table.sql
-- Criação da tabela de clientes para o customer-service

CREATE TABLE customers (
                           id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           customer_code       VARCHAR(20) UNIQUE NOT NULL,
                           cpf                 VARCHAR(11) UNIQUE NOT NULL,
                           email               VARCHAR(255) UNIQUE NOT NULL,
                           full_name           VARCHAR(150) NOT NULL,
                           birth_date          DATE NOT NULL,
                           phone_number        VARCHAR(20) NOT NULL,

    -- Endereço embutido
                           street              VARCHAR(150),
                           number              VARCHAR(20),
                           complement          VARCHAR(100),
                           neighborhood        VARCHAR(100),
                           city                VARCHAR(100) NOT NULL,
                           state               VARCHAR(2) NOT NULL,
                           zip_code            VARCHAR(9) NOT NULL,

    -- Status e auditoria
                           status              VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                           created_at          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           updated_at          TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           deactivated_at      TIMESTAMP WITH TIME ZONE,
                           deactivated_reason  VARCHAR(255),

                           CONSTRAINT chk_state CHECK (state ~ '^[A-Z]{2}$'),
    CONSTRAINT chk_zip_code CHECK (zip_code ~ '^\d{5}-?\d{3}$')
);

-- Índices para melhorar performance
CREATE INDEX idx_customers_cpf ON customers(cpf);
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_customers_status ON customers(status);
CREATE INDEX idx_customers_customer_code ON customers(customer_code);

COMMENT ON TABLE customers IS 'Tabela principal de clientes do banco digital';
COMMENT ON COLUMN customers.cpf IS 'CPF único do cliente';
COMMENT ON COLUMN customers.customer_code IS 'Código legível para o cliente';