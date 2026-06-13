-- Optional demo data for manager screens (run in phpMyAdmin if you already created the schema once).
USE hostel_maintenance;

DELETE FROM request_status_history WHERE request_id IN ('REQ001', 'REQ002', 'REQ003');
DELETE FROM maintenance_requests WHERE request_id IN ('REQ001', 'REQ002', 'REQ003');

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
