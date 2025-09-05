-- V1__create_users_table.sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,

    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,

    last_login TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP,

    CONSTRAINT chk_role CHECK (role IN ('ADMIN', 'EMPLOYER', 'CUSTOMER', 'DEVELOPER')),
    CONSTRAINT chk_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED'))
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
