-- Seed subjects (idempotent)
INSERT INTO subject_t (subject_code, subject_name, credit)
SELECT 'JAVA', 'Java Programming', 4
WHERE NOT EXISTS (SELECT 1 FROM subject_t WHERE subject_code = 'JAVA');

INSERT INTO subject_t (subject_code, subject_name, credit)
SELECT 'PHP', 'PHP Programming', 3
WHERE NOT EXISTS (SELECT 1 FROM subject_t WHERE subject_code = 'PHP');

INSERT INTO subject_t (subject_code, subject_name, credit)
SELECT 'WDA', 'Web Development and Applications', 3
WHERE NOT EXISTS (SELECT 1 FROM subject_t WHERE subject_code = 'WDA');

