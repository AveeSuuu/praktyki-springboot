--liquibase formatted sql
--changeset konrad.niedzielski:17

CREATE TABLE payment (
    payment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL,
    payment_status VARCHAR(32) NOT NULL,
    receive_date DATE NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(order_id)
);