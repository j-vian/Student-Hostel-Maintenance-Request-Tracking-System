DATABASE SETUP (one-time)
=========================

1. Start MySQL in XAMPP (green status on port 3306).
2. Open phpMyAdmin: http://localhost/phpmyadmin
3. Click the SQL tab.
4. Open hostel_maintenance_schema.sql in this folder, copy all contents, paste into SQL, click Go.

Manager admin logins (password for all: umpsaADMINs3cr37):
  shmrts-admin@admin.com.my
  umpsa-admin@admin.com.my
  hostelcare-admin@admin.com.my
  youradminusername@admin.com.my  (legacy seed account)

Demo student login:
  Email:    john123@gmail.com
  Password: student123

Demo staff login:
  Email:    farouq001@gmail.com
  Password: staff123

If you already ran an older schema and only need the new manager accounts,
run add_manager_accounts.sql instead of re-running the full schema.

If you changed the MySQL port from 3306, update DatabaseConfig.java to match.

If you already ran the schema once and need the demo requests (REQ001-REQ003) again,
run sample_requests.sql in phpMyAdmin.
