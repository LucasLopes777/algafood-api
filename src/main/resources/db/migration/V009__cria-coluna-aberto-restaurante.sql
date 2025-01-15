-- Verifica se a coluna 'ativo' já existe
SELECT COUNT(*) INTO @col_exists
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'restaurante'
AND COLUMN_NAME = 'aberto';

-- Adiciona a coluna apenas se não existir
SET @query = IF(@col_exists = 0, 
    'ALTER TABLE restaurante ADD aberto TINYINT(1) NOT NULL;',
    'SELECT "Coluna já existe";');

PREPARE stmt FROM @query;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
