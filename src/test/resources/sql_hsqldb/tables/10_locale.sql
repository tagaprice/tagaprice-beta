CREATE TABLE locale (
	locale_id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	fallback_id INTEGER,
	title varchar(50), --TODO fix NOT NULL,
	localTitle varchar(50), --TODO fix NOT NULL,

	created_at TIMESTAMP DEFAULT NOW(), --TODO fix NOT NULL,

	PRIMARY KEY (locale_id),
--TODO fix	FOREIGN KEY (fallback_id) REFERENCES locale (locale_id)
);
