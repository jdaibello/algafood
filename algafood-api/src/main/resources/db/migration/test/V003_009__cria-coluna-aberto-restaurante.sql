ALTER TABLE restaurant ADD COLUMN IF NOT EXISTS opened TINYINT(1) NOT NULL;
UPDATE restaurant SET opened = false;