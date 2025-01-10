CREATE TABLE address (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    address VARCHAR(500) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    pin_code VARCHAR(20) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_user_address FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO Address (user_id, address, city, state, country, pin_code)
SELECT id, 'Default Street and area', 'Default City', 'Default State', 'Default Country', '000000'
FROM users
WHERE id NOT IN (SELECT DISTINCT user_id FROM Address);


TRUNCATE TABLE purchase_order CASCADE;
ALTER TABLE purchase_order ADD COLUMN address_id BIGINT NOT NULL;
ALTER TABLE purchase_order ADD CONSTRAINT fk_order_address FOREIGN KEY (address_id) REFERENCES Address (id) ON DELETE SET NULL;

INSERT INTO purchase_order (user_id, total_cost, status, address_id) VALUES (2, 2499.95, 'PENDING',
(select id from address where user_id = 2 limit 1));