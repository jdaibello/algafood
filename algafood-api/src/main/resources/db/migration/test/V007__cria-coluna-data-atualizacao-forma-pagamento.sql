ALTER TABLE payment_method ADD COLUMN IF NOT EXISTS update_date DATETIME NULL;
UPDATE payment_method SET update_date = CURRENT_TIMESTAMP();
ALTER TABLE payment_method MODIFY update_date DATETIME NOT NULL;