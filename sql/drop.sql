DROP TABLE IF EXISTS productRevision;

DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS entityProperty;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS typeRevision;

DROP TABLE IF EXISTS currency;
DROP TABLE IF EXISTS productType;
DROP TABLE IF EXISTS property;
DROP TABLE IF EXISTS unit;
ALTER TABLE entity DROP CONSTRAINT fkey_currentRev;
DROP TABLE IF EXISTS entityRevision;

DROP TABLE IF EXISTS entity;

DROP TABLE IF EXISTS locale;
