CREATE TABLE inventory (
    id int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name char(30) NOT NULL,
    price int(10) NOT NULL,
    quantity int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE inventory_history (
    id int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    transaction_id char(36) NOT NULL,
    inventory_id int(10) NOT NULL,
    quantity int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;