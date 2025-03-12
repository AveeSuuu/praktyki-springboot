--liquibase formatted sql
--changeset konrad.niedzielski:19

ALTER TABLE payments
DROP COLUMN payment_status;