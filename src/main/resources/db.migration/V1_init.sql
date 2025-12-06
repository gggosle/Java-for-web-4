-- ==========================================
-- USERS (one-to-one with CUSTOMER)
-- ==========================================
CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(255) UNIQUE NOT NULL,
                          password_hash VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP DEFAULT NOW()
);

-- ==========================================
-- CUSTOMER (one-to-one with USER)
-- ==========================================
CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          full_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,

                          user_id BIGINT UNIQUE,          -- UNIQUE ensures 1-to-1
                          CONSTRAINT fk_customer_user
                              FOREIGN KEY (user_id) REFERENCES app_user(id)
                                  ON DELETE SET NULL
);

-- ==========================================
-- ORDERS (many-to-one to CUSTOMER)
-- ==========================================
CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        order_date TIMESTAMP DEFAULT NOW(),
                        status VARCHAR(50),

                        customer_id BIGINT NOT NULL,
                        CONSTRAINT fk_orders_customer
                            FOREIGN KEY (customer_id) REFERENCES customer(id)
                                ON DELETE CASCADE
);

-- ==========================================
-- PRODUCT
-- ==========================================
CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price NUMERIC(10, 2) NOT NULL
);

-- ==========================================
-- PRODUCT_ORDER (many-to-many: ORDER ↔ PRODUCT)
-- ==========================================
CREATE TABLE product_order (
                               order_id BIGINT NOT NULL,
                               product_id BIGINT NOT NULL,
                               quantity INT NOT NULL DEFAULT 1,

                               PRIMARY KEY (order_id, product_id),

                               CONSTRAINT fk_po_order
                                   FOREIGN KEY (order_id) REFERENCES orders(id)
                                       ON DELETE CASCADE,

                               CONSTRAINT fk_po_product
                                   FOREIGN KEY (product_id) REFERENCES product(id)
                                       ON DELETE CASCADE
);
