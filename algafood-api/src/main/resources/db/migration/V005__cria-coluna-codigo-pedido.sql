ALTER TABLE `order` ADD COLUMN code VARCHAR(36) NOT NULL AFTER id;
UPDATE `order` SET code = UUID();
ALTER TABLE `order` ADD CONSTRAINT uk_order_code UNIQUE (code);
