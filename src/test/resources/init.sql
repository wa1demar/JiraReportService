CREATE TABLE "comments" (
  id INTEGER PRIMARY KEY,
  report_id INTEGER,
  sprint_id INTEGER,
  text VARCHAR(255),
  creator VARCHAR(255),
  created_date TIMESTAMP
);