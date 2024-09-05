DROP TABLE gifts;
CREATE TABLE IF NOT EXISTS gifts (
                       id BIGSERIAL PRIMARY KEY,
                       child_id VARCHAR(800),
                       title VARCHAR(255),
                       price INT,
                       created_at TIMESTAMP
);