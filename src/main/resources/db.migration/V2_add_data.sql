
BEGIN;

-- USERS
INSERT INTO app_user (id, username, password_hash, created_at) VALUES
                                                                   (1, 'alice', '$2a$10$abcdefghijklmnopqrstuvABCDEFGHIJKL', NOW()),
                                                                   (2, 'bob',   '$2a$10$bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', NOW()),
                                                                   (3, 'carol', '$2a$10$cccccccccccccccccccccccccccccccccc', NOW())
    ON CONFLICT (id) DO NOTHING;

-- CUSTOMERS (one-to-one with USERS)
INSERT INTO customer (id, full_name, email, user_id) VALUES
                                                         (1, 'Alice Anderson', 'alice@example.com', 1),
                                                         (2, 'Bob Brown',     'bob@example.com',   2),
                                                         (3, 'Carol Chen',    'carol@example.com', 3),
                                                         (4, 'Guest Shopper', 'guest@example.com', NULL) -- guest without linked user
    ON CONFLICT (id) DO NOTHING;

-- PRODUCTS
INSERT INTO product (id, name, description, price) VALUES
                                                       (1, 'Widget A', 'Basic widget', 9.99),
                                                       (2, 'Widget B', 'Advanced widget', 19.99),
                                                       (3, 'Gadget',   'Useful gadget', 29.50),
                                                       (4, 'Thingamajig', 'Novelty item', 4.75)
    ON CONFLICT (id) DO NOTHING;

-- ORDERS
INSERT INTO orders (id, order_date, status, customer_id) VALUES
                                                             (1, NOW() - INTERVAL '3 days', 'SHIPPED', 1),
                                                             (2, NOW() - INTERVAL '1 days', 'PROCESSING', 2),
                                                             (3, NOW(), 'NEW', 4)
    ON CONFLICT (id) DO NOTHING;

-- PRODUCT_ORDER (order items)
INSERT INTO product_order (order_id, product_id) VALUES
                                                               (1, 1),  -- Alice ordered Widget A
                                                               (1, 3),  -- Alice ordered Gadget
                                                               (2, 2),  -- Bob ordered Widget B
                                                               (3, 4)   -- Guest ordered Thingamajig
    ON CONFLICT (order_id, product_id) DO NOTHING;

-- Sync serial sequences to max(id) to avoid future conflicts when inserting without explicit id
SELECT setval(pg_get_serial_sequence('app_user','id'), COALESCE(MAX(id), 1)) FROM app_user;
SELECT setval(pg_get_serial_sequence('customer','id'), COALESCE(MAX(id), 1)) FROM customer;
SELECT setval(pg_get_serial_sequence('product','id'), COALESCE(MAX(id), 1)) FROM product;
SELECT setval(pg_get_serial_sequence('orders','id'), COALESCE(MAX(id), 1)) FROM orders;

COMMIT;
