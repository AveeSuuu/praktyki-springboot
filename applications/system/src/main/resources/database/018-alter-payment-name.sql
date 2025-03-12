--liquibase formatted sql
--changeset konrad.niedzielski:18
ALTER TABLE payment RENAME TO payments;