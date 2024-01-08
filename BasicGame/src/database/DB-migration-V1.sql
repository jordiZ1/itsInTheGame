DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;


CREATE TABLE player (
    player_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    xp BIGINT NOT NULL DEFAULT 0,
    games_played BIGINT DEFAULT 0,
    games_won BIGINT DEFAULT 0
);

CREATE TABLE god (
    god_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    element_id BIGINT NOT NULL,
    health BIGINT NOT NULL,
    image VARCHAR(255) NOT NULL
);

CREATE TABLE player_god (
    player_id BIGINT CONSTRAINT fk_player REFERENCES player(player_id),
    god_id BIGINT CONSTRAINT fk_god REFERENCES god(god_id),
    level SMALLINT NOT NULL DEFAULT 1,
    PRIMARY KEY (player_id, god_id)
);

CREATE TABLE element (
    element_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE attack (
    attack_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    element_id BIGINT CONSTRAINT fk_element REFERENCES element(element_id),
    base_damage BIGINT NOT NULL
);

CREATE TABLE god_attack (
    god_id BIGINT NOT NULL CONSTRAINT fk_god REFERENCES god(god_id),
    attack_id BIGINT NOT NULL CONSTRAINT fk_attack REFERENCES attack(attack_id),
    PRIMARY KEY (god_id, attack_id)
);

ALTER TABLE god ADD CONSTRAINT fk_element FOREIGN KEY (element_id) REFERENCES element(element_id);

INSERT INTO player
    (name)
VALUES
    ('player1'),
    ('player2'),
    ('player3'),
    ('player4');

INSERT INTO element
    (name)
VALUES
    ('Thunder'),
    ('Magic');

INSERT INTO attack
    (name, element_id, base_damage)
VALUES
    ('attack1', 1, 15),
    ('attack2', 1, 20),
    ('attack3', 1, 25),
    ('attack4', 2, 15),
    ('attack5', 2, 20),
    ('attack6', 2, 25);

INSERT INTO god
    (name, category, element_id, health, image)
VALUES
    ('Thor', 'Norse', 1, 100, 'BasicGame/dummy1.png'),
    ('Loki', 'Norse', 2, 100, 'BasicGame/dummy2.png');

INSERT INTO god_attack
    (god_id, attack_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (2, 6);