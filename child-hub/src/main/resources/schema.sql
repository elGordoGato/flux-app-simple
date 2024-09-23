DROP TABLE if exists children;
CREATE TABLE children (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          age INT,
                          created_at TIMESTAMP
);