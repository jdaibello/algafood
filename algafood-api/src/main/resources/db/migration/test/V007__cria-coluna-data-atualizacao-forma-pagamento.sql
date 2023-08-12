ALTER TABLE payment_method ADD update_date DATETIME NULL;
UPDATE payment_method SET update_date = UTC_TIMESTAMP();
ALTER TABLE payment_method MODIFY update_date DATETIME NOT NULL;