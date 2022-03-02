## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE rec_dev;
CREATE DATABASE rec_prod;

#Create database service accounts
CREATE USER 'rec_dev_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'rec_prod_user'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'rec_dev_user'@'%' IDENTIFIED BY 'password';
CREATE USER 'rec_prod_user'@'%' IDENTIFIED BY 'password';

#Database grants
GRANT SELECT ON rec_dev.* to 'rec_dev_user'@'localhost';
GRANT INSERT ON rec_dev.* to 'rec_dev_user'@'localhost';
GRANT DELETE ON rec_dev.* to 'rec_dev_user'@'localhost';
GRANT UPDATE ON rec_dev.* to 'rec_dev_user'@'localhost';
GRANT SELECT ON rec_prod.* to 'rec_prod_user'@'localhost';
GRANT INSERT ON rec_prod.* to 'rec_prod_user'@'localhost';
GRANT DELETE ON rec_prod.* to 'rec_prod_user'@'localhost';
GRANT UPDATE ON rec_prod.* to 'rec_prod_user'@'localhost';
GRANT SELECT ON rec_dev.* to 'rec_dev_user'@'%';
GRANT INSERT ON rec_dev.* to 'rec_dev_user'@'%';
GRANT DELETE ON rec_dev.* to 'rec_dev_user'@'%';
GRANT UPDATE ON rec_dev.* to 'rec_dev_user'@'%';
GRANT SELECT ON rec_prod.* to 'rec_prod_user'@'%';
GRANT INSERT ON rec_prod.* to 'rec_prod_user'@'%';
GRANT DELETE ON rec_prod.* to 'rec_prod_user'@'%';
GRANT UPDATE ON rec_prod.* to 'rec_prod_user'@'%';