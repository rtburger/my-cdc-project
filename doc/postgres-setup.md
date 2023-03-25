# PostreSQL Install
[Install and Configure PostgreSQL](https://ubuntu.com/server/docs/databases-postgresql)

# Steps
`sudo -u postgres psql`

`postgres=# \conninfo`

You are connected to database "postgres" as user "postgres" via socket in "/var/run/postgresql" at port "5432".

`postgres=# \l`
                               
List of databases

   Name    |  Owner   | Encoding |   Collate   |    Ctype    |   Access privileges   
-----------+----------+----------+-------------+-------------+-----------------------
 postgres  | postgres | UTF8     | en_CA.UTF-8 | en_CA.UTF-8 | 
 template0 | postgres | UTF8     | en_CA.UTF-8 | en_CA.UTF-8 | =c/postgres          +
           |          |          |             |             | postgres=CTc/postgres
 template1 | postgres | UTF8     | en_CA.UTF-8 | en_CA.UTF-8 | =c/postgres          +
           |          |          |             |             | postgres=CTc/postgres
(3 rows)

`postgres=# \du`

                                   List of roles
 Role name |                         Attributes                         | Member of 
-----------+------------------------------------------------------------+-----------
 postgres  | Superuser, Create role, Create DB, Replication, Bypass RLS | {}

`postgres=# create database test_erp;`

CREATE DATABASE

`postgres=# \c test_erp`

You are now connected to database "test_erp" as user "postgres".

`test_erp=# \dt`

Did not find any relations.

`test_erp=# CREATE TABLE clients (id SERIAL PRIMARY KEY, first_name VARCHAR, last_name VARCHAR, role VARCHAR);`

CREATE TABLE

`test_erp=# \dt`

          List of relations
 Schema |  Name   | Type  |  Owner   
--------+---------+-------+----------
 public | clients | table | postgres
(1 row)

`test_erp=# INSERT INTO clients (first_name, last_name, role) VALUES ('John', 'Smith', 'CEO');`

INSERT 0 1

`test_erp=# select * from clients;`

 id | first_name | last_name | role 
----+------------+-----------+------
  1 | John       | Smith     | CEO
(1 row)
