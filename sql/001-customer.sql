-- DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer (
  id SERIAL,
  last_name VARCHAR(255),
  first_name VARCHAR(255)
);

INSERT INTO customer ( last_name, first_name ) VALUES
  ( '一郎', '佐藤' ),
  ( '二郎', '佐藤' ),
  ( '三郎', '佐藤' ),
  ( '一郎', '鈴木' ),
  ( '二郎', '鈴木' ),
  ( '三郎', '鈴木' ),
  ( '一郎', '高橋' ),
  ( '二郎', '高橋' ),
  ( '三郎', '高橋' )
;
