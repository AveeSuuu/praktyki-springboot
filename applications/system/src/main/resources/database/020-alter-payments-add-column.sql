--liquibase formatted sql
--changeset konrad.niedzielski:20

ALTER TABLE payments
ADD COLUMN cost DECIMAL NOT NULL;