--liquibase formatted sql
--changeset root:20220413123715
--type create
BEGIN;

CREATE TABLE IF NOT EXISTS order_bcs_orders
(
    id BINARY(16) NOT NULL PRIMARY KEY COMMENT 'Order AggregateRoot UUID',
    user_id BINARY(16) NOT NULL COMMENT 'User BCs User AG ID',
    code VARCHAR(12) NOT NULL COMMENT 'ORDER CODE',
    product_name VARCHAR(100) NOT NULL COMMENT 'ORDERED PRODUCT NAME',
    ordered_at DATETIME NOT NULL COMMENT 'ORDERED AT',
    created_at DATETIME COMMENT 'CREATED AT',
    updated_at DATETIME COMMENT 'UPDATED AT',
    deleted_at DATETIME COMMENT 'AGGREGATE ROOT DELETED AT (NOT NULL EQ SOFT DELETED)',
    INDEX(user_id, ordered_at DESC),
    INDEX(user_id, updated_at DESC)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT 'USER Boundary Contexts User Aggregate Root';

COMMIT;
