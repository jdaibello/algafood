ALTER TABLE restaurant ADD COLUMN active TINYINT(1) NOT NULL;
UPDATE restaurant SET active = true;