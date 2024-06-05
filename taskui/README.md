# user + db creation

```
CREATE USER taskrunner_readwriter WITH ENCRYPTED PASSWORD 'xfdz8t-mds-V';
CREATE DATABASE taskrunner_db WITH OWNER taskrunner_readwriter;
\c taskrunner_db
create table tasks (
  name varchar(255) NOT NULL PRIMARY KEY,
  fileName varchar(255) NOT NULL,
  minute SMALLINT,
  hour SMALLINT,
  status varchar(255)
);

INSERT INTO tasks (name, fileName, minute, hour, status) VALUES ('taskA', 'TaskA.kt', null, null, 'SCHEDULED');
INSERT INTO tasks (name, fileName, minute, hour, status) VALUES ('taskB', 'taskB.kt', 1, 2, 'SCHEDULED');
```