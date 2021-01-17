# How to configure sandbox database

## create database container

```bash
$ docker run \
  -e MYSQL_ROOT_PASSWORD=password \
  -e MYSQL_USER=test \
  -e MYSQL_PASSWORD=password \
  -e MYSQL_DATABASE=test \
  -e TZ=Asia/Tokyo \
  -p 3306:3306 \
  --name mysql_sandbox \
  -d mysql:5.7 \
  --character-set-server=utf8
```

## Table Data

```sql
USE
test;

DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    id      INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    value   INT(10) NOT NULL,
    created DATETIME    NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice
(
    id      INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    created DATETIME NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS invoice_detail;
CREATE TABLE invoice_detail
(
    id         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    invoice_id INT(10) UNSIGNED NOT NULL,
    product_id INT(10) UNSIGNED NOT NULL,
    quantity   INT(10) UNSIGNED NOT NULL,
    created    DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_invoice
        FOREIGN KEY (invoice_id)
            REFERENCES invoice (id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
```