-- Student Hostel Maintenance Request & Tracking System
-- Run this entire script once in phpMyAdmin (SQL tab) on database hostel_maintenance_db.

CREATE DATABASE IF NOT EXISTS hostel_maintenance_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE hostel_maintenance_db;

DROP TABLE IF EXISTS request_status_history;
DROP TABLE IF EXISTS maintenance_requests;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS rooms;

CREATE TABLE rooms (
    room_id      INT AUTO_INCREMENT PRIMARY KEY,
    room_number  VARCHAR(20)  NOT NULL,
    place_name   VARCHAR(100) NOT NULL,
    UNIQUE KEY uq_room_place (room_number, place_name)
);

CREATE TABLE users (
    user_id          VARCHAR(20)  PRIMARY KEY,
    username         VARCHAR(50)  NOT NULL UNIQUE,
    first_name       VARCHAR(50)  NOT NULL,
    last_name        VARCHAR(50)  NOT NULL,
    email            VARCHAR(100) NOT NULL UNIQUE,
    password         VARCHAR(255) NOT NULL,
    role             VARCHAR(20)  NOT NULL,
    room_id          INT          NULL,
    staff_role       VARCHAR(50)  NULL,
    other_expertise  VARCHAR(100) NULL,
    CONSTRAINT fk_users_room
        FOREIGN KEY (room_id) REFERENCES rooms (room_id)
);

CREATE TABLE maintenance_requests (
    request_id         VARCHAR(20) PRIMARY KEY,
    student_id         VARCHAR(20) NOT NULL,
    room_id            INT         NOT NULL,
    request_type       VARCHAR(20) NOT NULL,
    description        TEXT        NOT NULL,
    priority           VARCHAR(10) NOT NULL,
    status             VARCHAR(20) NOT NULL,
    assigned_staff_id  VARCHAR(20) NULL,
    date_raised        DATETIME    NOT NULL,
    CONSTRAINT fk_requests_student
        FOREIGN KEY (student_id) REFERENCES users (user_id),
    CONSTRAINT fk_requests_room
        FOREIGN KEY (room_id) REFERENCES rooms (room_id),
    CONSTRAINT fk_requests_staff
        FOREIGN KEY (assigned_staff_id) REFERENCES users (user_id)
);

CREATE TABLE request_status_history (
    history_id  INT AUTO_INCREMENT PRIMARY KEY,
    request_id  VARCHAR(20) NOT NULL,
    status      VARCHAR(20) NOT NULL,
    changed_at  DATETIME    NOT NULL,
    changed_by  VARCHAR(20) NULL,
    CONSTRAINT fk_history_request
        FOREIGN KEY (request_id) REFERENCES maintenance_requests (request_id),
    CONSTRAINT fk_history_user
        FOREIGN KEY (changed_by) REFERENCES users (user_id)
);

-- Manager admin accounts (password for all: umpsaADMINs3cr37)
INSERT INTO users (user_id, username, first_name, last_name, email, password, role)
VALUES
    ('ADM001', 'hosteladmin', 'System', 'Administrator', 'youradminusername@admin.com.my', 'umpsaADMINs3cr37', 'MANAGER'),
    ('ADM002', 'shmrtsadmin', 'SHMRTS', 'Administrator', 'shmrts-admin@admin.com.my', 'umpsaADMINs3cr37', 'MANAGER'),
    ('ADM003', 'umpsaadmin', 'UMPSA', 'Administrator', 'umpsa-admin@admin.com.my', 'umpsaADMINs3cr37', 'MANAGER'),
    ('ADM004', 'hostelcareadmin', 'Hostel Care', 'Administrator', 'hostelcare-admin@admin.com.my', 'umpsaADMINs3cr37', 'MANAGER');

-- Optional demo rooms/users for testing (safe to delete later).
INSERT INTO rooms (room_number, place_name) VALUES
    ('A-10-05', 'DHUAM'),
    ('B-10-05', 'KK5');

INSERT INTO users (user_id, username, first_name, last_name, email, password, role, room_id)
VALUES (
    'CB25174',
    'vjohn',
    'John',
    'Vianney',
    'john123@gmail.com',
    'student123',
    'STUDENT',
    1
);

INSERT INTO users (user_id, username, first_name, last_name, email, password, role, staff_role)
VALUES
    ('ST001', 'kfarouq', 'Ku', 'Farouq', 'farouq001@gmail.com', 'staff123', 'STAFF', 'Electrician'),
    ('ST002', 'wfaiz', 'Wazif', 'Faiz', 'faiz002@gmail.com', 'staff123', 'STAFF', 'Plumber');

INSERT INTO maintenance_requests
    (request_id, student_id, room_id, request_type, description, priority, status, assigned_staff_id, date_raised)
VALUES
    ('REQ001', 'CB25174', 1, 'electrical', 'Power socket not working near study desk.', 'High', 'SUBMITTED', NULL, NOW()),
    ('REQ002', 'CB25174', 1, 'plumbing', 'Sink is draining very slowly.', 'Medium', 'IN_PROGRESS', 'ST002', NOW()),
    ('REQ003', 'CB25174', 1, 'furniture', 'Wardrobe door hinge is loose.', 'Low', 'COMPLETED', 'ST001', NOW());

INSERT INTO request_status_history (request_id, status, changed_at, changed_by)
VALUES
    ('REQ001', 'SUBMITTED', NOW(), 'CB25174'),
    ('REQ002', 'SUBMITTED', NOW(), 'CB25174'),
    ('REQ002', 'IN_PROGRESS', NOW(), 'ADM001'),
    ('REQ003', 'SUBMITTED', NOW(), 'CB25174'),
    ('REQ003', 'IN_PROGRESS', NOW(), 'ADM001'),
    ('REQ003', 'COMPLETED', NOW(), 'ST001');
