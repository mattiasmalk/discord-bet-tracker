ALTER TABLE bet
    ADD description VARCHAR(255);

ALTER TABLE bet
    ADD odds DECIMAL(19,3);

ALTER TABLE bet
    ADD stake DECIMAL(9,3);

ALTER TABLE bet
    ADD status VARCHAR(255);

CREATE UNIQUE INDEX IX_pk_user ON "USER" (user_id, server_id);
