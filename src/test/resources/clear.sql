CREATE TABLE comments (
  "id" INT PRIMARY KEY,
  "report_id" INT,
  "sprint_id" INT,
  "text" TEXT ,
  "creator" VARCHAR(150),
  "created_date" DATETIME
);

CREATE TABLE configs (
  "id" INT PRIMARY KEY,
  "base_url" VARCHAR(150),
  "jira_user" VARCHAR(150),
  "story_point_name" VARCHAR(150),
  "agile_done_name" VARCHAR(150),
  "jira_dev_group_name" VARCHAR(150),
  "bug_name" VARCHAR(150),
  "path_to_ajax" VARCHAR(150),
  "path_to_agile_rest" VARCHAR(150),
  "non_working_days" VARCHAR(150),
  "auto_sync_time" VARCHAR(150),
  "jira_password" VARCHAR
);