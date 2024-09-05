DROP TABLE if exists gifts;
CREATE TABLE children (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          age INT,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP
);