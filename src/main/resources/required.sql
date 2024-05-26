create database task2;
use task2;

CREATE TABLE customers (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          fname VARCHAR(50),
                          lname VARCHAR(50),
                          phone VARCHAR(15) UNIQUE,
                          dob VARCHAR(10),
                          account_no VARCHAR(12) UNIQUE,
                          amount DOUBLE,
                          pin INT
);

CREATE TABLE history (
                         customer_id INT NOT NULL,
                         timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         amount DOUBLE NOT NULL,
                         transaction_type varchar(20) not null,
                         FOREIGN KEY (customer_id) REFERENCES customers(id)
);


INSERT INTO customers (fname, lname, phone, dob, account_no, amount, pin) VALUES
                                                                              ('John', 'Doe', '1234567890', '1990-01-01', 'SB00000001', 5000.0, 123),
                                                                              ('Jane', 'Doe', '1234567891', '1991-02-02', 'SB00000002', 6000.0, 234),
                                                                              ('Alice', 'Smith', '1234567892', '1992-03-03', 'SB00000003', 7000.0, 345),
                                                                              ('Bob', 'Johnson', '1234567893', '1993-04-04', 'SB00000004', 8000.0, 456),
                                                                              ('Charlie', 'Brown', '1234567894', '1994-05-05', 'SB00000005', 9000.0, 567);

-- Transactions for John Doe
INSERT INTO history (customer_id, amount, transaction_type) VALUES
                                                                (1, 1000.0, 'deposit'),
                                                                (1, 500.0, 'withdraw'),
                                                                (1, 200.0, 'transfer');

-- Transactions for Jane Doe
INSERT INTO history (customer_id, amount, transaction_type) VALUES
                                                                (2, 1500.0, 'deposit'),
                                                                (2, 700.0, 'withdraw'),
                                                                (2, 300.0, 'transfer');

-- Transactions for Alice Smith
INSERT INTO history (customer_id, amount, transaction_type) VALUES
                                                                (3, 2000.0, 'deposit'),
                                                                (3, 800.0, 'withdraw'),
                                                                (3, 400.0, 'transfer');

-- Transactions for Bob Johnson
INSERT INTO history (customer_id, amount, transaction_type) VALUES
                                                                (4, 2500.0, 'deposit'),
                                                                (4, 900.0, 'withdraw'),
                                                                (4, 500.0, 'transfer');

-- Transactions for Charlie Brown
INSERT INTO history (customer_id, amount, transaction_type) VALUES
                                                                (5, 3000.0, 'deposit'),
                                                                (5, 1000.0, 'withdraw'),
                                                                (5, 600.0, 'transfer');


