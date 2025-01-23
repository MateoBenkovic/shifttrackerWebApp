

-- Drop tables if they exist
DROP TABLE IF EXISTS app_role CASCADE;
DROP TABLE IF EXISTS app_user CASCADE;
DROP TABLE IF EXISTS org_unit CASCADE;
DROP TABLE IF EXISTS schedule_team_per_shift CASCADE;
DROP TABLE IF EXISTS shift CASCADE;
DROP TABLE IF EXISTS team CASCADE;
DROP TABLE IF EXISTS team_role CASCADE;

-- Create tables
CREATE TABLE app_role (
    id SERIAL PRIMARY KEY,           -- Use SERIAL for auto-increment in PostgreSQL
    order_number INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE org_unit (
    id SERIAL PRIMARY KEY,           -- Use SERIAL for auto-increment in PostgreSQL
    order_number INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE shift (
    id SERIAL PRIMARY KEY,           -- Use SERIAL for auto-increment in PostgreSQL
    start_time TIMESTAMP(6),
    end_time TIMESTAMP(6),
    name VARCHAR(255) NOT NULL
);

CREATE TABLE team (
    id SERIAL PRIMARY KEY,           -- Use SERIAL for auto-increment in PostgreSQL
    order_number INTEGER NOT NULL,
    type INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE team_role (
    id SERIAL PRIMARY KEY,           -- Use SERIAL for auto-increment in PostgreSQL
    order_number INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE schedule_team_per_shift (
    id SERIAL PRIMARY KEY,           -- Use SERIAL for auto-increment in PostgreSQL
    schedule_per_month_id INTEGER NOT NULL,
    shift_id INTEGER UNIQUE,         -- UNIQUE constraint
    team_id INTEGER UNIQUE,          -- UNIQUE constraint
    date TIMESTAMP(6),               -- Correct format for TIMESTAMP with microseconds
    CONSTRAINT FKd8pq9b2ohy8bvs8scqptkbf48 FOREIGN KEY (shift_id) REFERENCES shift(id),
    CONSTRAINT FKhcllx4itx0168yl4vfp6ci2ws FOREIGN KEY (team_id) REFERENCES team(id)
);

CREATE TABLE app_user (
    id SERIAL PRIMARY KEY,           -- Use SERIAL for auto-increment in PostgreSQL
    app_role_id INTEGER,
    org_unit_id INTEGER,
    schedule_team_per_shift_id INTEGER,
    team_id INTEGER,
    team_role_id INTEGER,
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    oib VARCHAR(255),
    telephone VARCHAR(255),
    app_user_id INTEGER,             -- You need to have this field if you want to reference schedule_team_per_shift
    CONSTRAINT FK2lqvmk127ira6ybqnlrq5bkx4 FOREIGN KEY (app_role_id) REFERENCES app_role(id),
    CONSTRAINT FK6jnvlspja49tr23aur70rtcou FOREIGN KEY (org_unit_id) REFERENCES org_unit(id),
    CONSTRAINT FKngdkemk0odrql9wdpjj01qopm FOREIGN KEY (schedule_team_per_shift_id) REFERENCES schedule_team_per_shift(id),
    CONSTRAINT FKaauds0ajouf9m311rl6vf57dm FOREIGN KEY (team_id) REFERENCES team(id),
    CONSTRAINT FKbxuqlbsbrw3t50l0bl119n8v1 FOREIGN KEY (team_role_id) REFERENCES team_role(id),
    CONSTRAINT FKc2nh3e8grlvty246x58qplqye FOREIGN KEY (app_user_id) REFERENCES schedule_team_per_shift(id)
);
