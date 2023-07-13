CREATE TABLE pfm_db_version
(
  db_version character varying(10) NOT NULL DEFAULT ''::character varying,
  insert_date timestamp without time zone NOT NULL,
  CONSTRAINT pfm_db_version_pkey PRIMARY KEY (db_version)
)
;
COMMENT ON TABLE pfm_db_version IS 'DBバージョン情報';
COMMENT ON COLUMN pfm_db_version.db_version IS 'DBバージョン';
COMMENT ON COLUMN pfm_db_version.insert_date IS '登録日';

INSERT INTO pfm_db_version VALUES('4.6-1', now());
