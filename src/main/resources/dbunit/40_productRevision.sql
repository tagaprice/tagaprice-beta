CREATE TABLE productRevision (
	ent_id BIGINT NOT NULL,
	rev INTEGER NOT NULL,

	unit VARCHAR(20) NOT NULL,
	amount DOUBLE NOT NULL, -- integer or double precision ?
	category_id BIGINT NULL,
	imageUrl varchar(100) NULL,

	PRIMARY KEY (ent_id, rev),
--	FOREIGN KEY (ent_id) REFERENCES product (ent_id),
--	FOREIGN KEY (ent_id, rev) REFERENCES entityRevision(ent_id, rev),
--	FOREIGN KEY (type_id) REFERENCES productType (type_id),
--	FOREIGN KEY (brand_id) REFERENCES brand (brand_id)
);