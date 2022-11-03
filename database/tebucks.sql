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

    -- constraints for Strings, must be specific values, status and type
    CONSTRAINT pk_transfer PRIMARY KEY (transfer_id),
    CONSTRAINT fk_transfer_users_from FOREIGN KEY (user_from) REFERENCES users (user_id),
    CONSTRAINT fk_transfer_users_to FOREIGN KEY (user_to) REFERENCES users (user_id),
    CONSTRAINT chk_transfer_type check (transfer_type IN ('Request','Send') ),
    CONSTRAINT chk_transfer_status check (transfer_status IN ('Pending','Approved','Rejected'))

);


COMMIT TRANSACTION;




