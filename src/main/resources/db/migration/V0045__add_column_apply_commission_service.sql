ALTER TABLE service
    ADD COLUMN apply_seller_commission BOOL DEFAULT FALSE NULL;
UPDATE service SET apply_seller_commission = false;
ALTER TABLE service MODIFY COLUMN apply_seller_commission BOOL DEFAULT FALSE NOT NULL;