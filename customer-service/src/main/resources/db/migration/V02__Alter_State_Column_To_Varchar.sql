-- V2__Alter_State_Column_To_Varchar.sql
-- Corrige o tipo da coluna state de CHAR(2) para VARCHAR(2) para compatibilidade com Hibernate

-- Remove constraint antiga se existir
ALTER TABLE customers DROP CONSTRAINT IF EXISTS chk_state;

-- Altera o tipo da coluna
ALTER TABLE customers
ALTER COLUMN state TYPE VARCHAR(2) USING state::VARCHAR(2);

-- Adiciona constraint novamente (agora com VARCHAR)
ALTER TABLE customers
    ADD CONSTRAINT chk_state CHECK (state ~ '^[A-Z]{2}$');

-- Comentário
COMMENT ON COLUMN customers.state IS 'UF do estado (ex: SP, RJ) - VARCHAR(2)';