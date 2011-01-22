CREATE TABLE category (
	category_id BIGINT NOT NULL,
	parent_id BIGINT NULL,
	

    title varchar(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(), --TODO fix NOT NULL,
    creator BIGINT NOT NULL,

	PRIMARY KEY (category_id),
	FOREIGN KEY (parent_id) REFERENCES category (category_id)
--TODO fix  FOREIGN KEY (creator_id) REFERENCES account (account_id),
);