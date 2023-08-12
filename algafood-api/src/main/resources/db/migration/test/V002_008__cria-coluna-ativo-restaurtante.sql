ALTER TABLE restaurant ADD COLUMN IF NOT EXISTS active TINYINT(1) NOT NULL;
UPDATE restaurant SET active = true;