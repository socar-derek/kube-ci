CREATE DATABASE IF NOT EXISTS kotlin_board;
USE kotlin_board;

CREATE TABLE tbl_board(
    b_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    b_title VARCHAR(50),
    b_contents VARCHAR(255)
);

insert into tbl_board(
                      b_title, b_contents
) VALUES ('hello','world');

insert into tbl_board(
    b_title, b_contents
) VALUES ('hello2','world2');