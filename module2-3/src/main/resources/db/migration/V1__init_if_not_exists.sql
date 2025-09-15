CREATE TABLE IF NOT EXISTS writers (
           id BIGSERIAL PRIMARY KEY,
           first_name VARCHAR(100) NOT NULL,
           last_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS labels (
          id BIGSERIAL PRIMARY KEY,
          name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS posts (
         id BIGSERIAL PRIMARY KEY,
         content TEXT NOT NULL,
         created TIMESTAMP NOT NULL,
         updated TIMESTAMP,
         writer_id BIGINT NOT NULL,
         status VARCHAR(20) NOT NULL DEFAULT 'UNDER_REVIEW',
         CONSTRAINT fk_post_writer FOREIGN KEY (writer_id)
             REFERENCES writers (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS posts_labels (
        post_id BIGINT NOT NULL,
        label_id BIGINT NOT NULL,
        PRIMARY KEY (post_id, label_id),
        CONSTRAINT fk_post FOREIGN KEY (post_id)
            REFERENCES posts (id) ON DELETE CASCADE,
        CONSTRAINT fk_label FOREIGN KEY (label_id)
            REFERENCES labels (id) ON DELETE CASCADE
);