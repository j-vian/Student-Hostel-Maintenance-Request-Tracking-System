-- Run in phpMyAdmin on an EXISTING hostel_maintenance_db if you already seeded the schema
-- and only need the new manager admin accounts (password: umpsaADMINs3cr37).

USE hostel_maintenance_db;

INSERT INTO users (user_id, username, first_name, last_name, email, password, role)
SELECT 'ADM002', 'shmrtsadmin', 'SHMRTS', 'Administrator', 'shmrts-admin@admin.com.my', 'umpsaADMINs3cr37', 'MANAGER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE LOWER(email) = 'shmrts-admin@admin.com.my');

INSERT INTO users (user_id, username, first_name, last_name, email, password, role)
SELECT 'ADM003', 'umpsaadmin', 'UMPSA', 'Administrator', 'umpsa-admin@admin.com.my', 'umpsaADMINs3cr37', 'MANAGER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE LOWER(email) = 'umpsa-admin@admin.com.my');

INSERT INTO users (user_id, username, first_name, last_name, email, password, role)
SELECT 'ADM004', 'hostelcareadmin', 'Hostel Care', 'Administrator', 'hostelcare-admin@admin.com.my', 'umpsaADMINs3cr37', 'MANAGER'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE LOWER(email) = 'hostelcare-admin@admin.com.my');

UPDATE users
SET password = 'umpsaADMINs3cr37'
WHERE role = 'MANAGER'
  AND LOWER(email) IN (
      'youradminusername@admin.com.my',
      'shmrts-admin@admin.com.my',
      'umpsa-admin@admin.com.my',
      'hostelcare-admin@admin.com.my'
  );
