ALTER TABLE restaurant ADD COLUMN opened TINYINT(1) NOT NULL;
UPDATE restaurant SET opened = false;