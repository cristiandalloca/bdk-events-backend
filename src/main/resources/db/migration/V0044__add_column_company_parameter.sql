ALTER TABLE company_parameter
    ADD COLUMN bill_issuance_fee_value DECIMAL(5,2) NULL;

ALTER TABLE company_parameter
    ADD COLUMN credit_card_fee_percentage DECIMAL(5,2) NULL;


ALTER TABLE company_parameter
    ADD COLUMN percentage_BDI DECIMAL(5,2) DEFAULT 0 NULL;
UPDATE company_parameter SET percentage_BDI = 0;
ALTER TABLE company_parameter MODIFY COLUMN percentage_BDI DECIMAL(5,2) DEFAULT 0 NOT NULL;



ALTER TABLE company_parameter
    ADD COLUMN max_percentage_seller_commission DECIMAL(5,2) DEFAULT 0 NULL;
UPDATE company_parameter SET max_percentage_seller_commission = 0;
ALTER TABLE company_parameter MODIFY COLUMN max_percentage_seller_commission DECIMAL(5,2) DEFAULT 0 NOT NULL;
