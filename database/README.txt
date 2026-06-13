DATABASE SETUP (one-time)
=========================

1. Start MySQL in XAMPP (green status on port 3306).
2. Open phpMyAdmin: http://localhost/phpmyadmin
3. Click the SQL tab.
4. Open hostel_maintenance_schema.sql in this folder, copy all contents, paste into SQL, click Go.

Default admin login (after running the script):
  Email:    youradminusername@admin.com.my
  Password: admin123

Demo student login:
  Email:    john123@gmail.com
  Password: student123

Demo staff login:
  Email:    farouq001@gmail.com
  Password: staff123

If you changed the MySQL port from 3306, update DatabaseConfig.java to match.
