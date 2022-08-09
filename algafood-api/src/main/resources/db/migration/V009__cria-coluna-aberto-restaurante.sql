ALTER TABLE restaurant ADD opened TINYINT(1) NOT NULL;
UPDATE restaurant SET opened = false;