ALTER TABLE country MODIFY COLUMN external_id char(36);
ALTER TABLE state MODIFY COLUMN external_id char(36);
ALTER TABLE city MODIFY COLUMN external_id char(36);
ALTER TABLE company MODIFY COLUMN external_id char(36);