BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, account, transfer;

CREATE TABLE users (
	user_id serial NOT NULL,
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	CONSTRAINT pk_users PRIMARY KEY (user_id),
	CONSTRAINT uq_username UNIQUE (username)
);



CREATE TABLE account (
    account_id serial NOT NULL,
    user_id int NOT NULL unique,
    balance decimal NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (account_id),
    CONSTRAINT fk_account_users FOREIGN KEY (user_id) REFERENCES users (user_id)

);

CREATE TABLE transfer (
    transfer_id serial NOT NULL,
    transfer_type varchar(50) NOT NULL,
    transfer_status varchar(50) NOT NULL,
    user_from int NOT NULL,
    user_to int NOT NULL,
    amount decimal NOT NULL,


    CONSTRAINT pk_transfer PRIMARY KEY (transfer_id),
    CONSTRAINT fk_transfer_users_from FOREIGN KEY (user_from) REFERENCES users (user_id),
    CONSTRAINT fk_transfer_users_to FOREIGN KEY (user_to) REFERENCES users (user_id),
    CONSTRAINT chk_transfer_type check (transfer_type IN ('Request','Send') ),
    CONSTRAINT chk_transfer_status check (transfer_status IN ('Pending','Approved','Rejected'))

);

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER'); -- 1
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER'); -- 2
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER'); -- 3

INSERT INTO account (user_id, balance) VALUES (1, 1000); -- 1
INSERT INTO account (user_id, balance) VALUES (2, 1000); -- 2
INSERT INTO account (user_id, balance) VALUES (3, 2000); -- 3

INSERT INTO transfer (transfer_type, transfer_status, user_from, user_to, amount) VALUES ('Send', 'Approved', 3, 2, 1100);
INSERT INTO transfer (transfer_type, transfer_status, user_from, user_to, amount) VALUES ('Send', 'Approved', 1, 2, 500);
INSERT INTO transfer (transfer_type, transfer_status, user_from, user_to, amount) VALUES ('Send', 'Approved', 3, 1, 400);

COMMIT TRANSACTION;

