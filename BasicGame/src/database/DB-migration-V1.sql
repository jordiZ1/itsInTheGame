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
    health BIGINT NOT NULL
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
    base_damage BIGINT NOT NULL,
    healing BIGINT NOT NULL
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
    ('TBD');

INSERT INTO attack
    (name, element_id, base_damage, healing)
VALUES
    -- Achilles
    ('Spear Throw', 1, 5, 0),
    ('Shield Bash', 1, 3, 0),
    ('Scorpion Slash', 1, 10, 0),

    -- Ares
    -- Spear Throw
    -- Shield Bash
    ('Battle Call', 1, 8, 0),

    -- Atlas
    ('Shoulder Press', 1, 3, 0),
    ('Cloud Blow', 1, 6, 0),
    ('Star Shower', 1, 12, 0),

    -- Apollo
    ('Arrow of Agreus', 1, 4, 0),
    ('Symphony of Strings', 1, 7, 0),
    ('Healing Truth', 1, 0, 15),

    -- Anhur
    ('Battle Roar', 1, 3, 0),
    ('Rise of Sand', 1, 6, 0),
    ('Desert Fury', 1, 10, 0), -- Needs dmg buff

    -- Anubis
    ('Grave Digger', 1, 5, 0),
    ('Harvest', 1, 4, 0),
    ('Hellraiser', 1, 12, 0),

    -- Sobek
    ('March of Pharos', 1, 7, 0),
    ('Bite of the Nile', 1, 3, 0),
    ('Desert Tsunami', 1, 9, 0),

    -- Horus
    ('Skyfall', 1, 5, 0),
    ('Light of the Throne', 1, 0, 5),
    ('Sky Splitter', 1, 20, 0),

    -- Odin
    ('Raven Attack', 1, 3, 0),
    ('Staff of Death', 1, 6, 0),
    ('Tree of Wisdom', 1, 30, -30),

    -- Thor
    ('Hammer Fall', 1, 5, 0),
    ('Lightning of Gods', 1, 8, 0),
    ('Hammer Storm', 1, 12, 0),

    -- Tyr
    ('Battle Cry', 1, 4, 0),
    ('Sun Eater', 1, 3, 0), -- Needs dmg stacks
    ('Sword of Victory', 1, 5, 0), -- Needs stack gaining

    -- Ullr
    ('Swift Arrow', 1, 4, 0),
    ('Freezing Axe', 1, 8, 0),
    ('Fury of Snow', 1, 15, 0),

    -- Ao Kuang
    ('Dragon Breath', 1, 5, 0),
    ('Drought Storm', 1, 8, 0),
    ('Ruler of Dragons', 1, 12, 0),

    -- Erlang Shen
    ('Spear of Strength', 1, 3, 0),
    ('Tiger Slayer', 1, 7, 0),
    ('Duel to the Death', 1, 10, 0),

    -- Guan Yu
    ('Cavalry', 1, 0, 1), -- Needs stack gaining
    ('Assault', 1, 8, 0),
    ('Battle Charge', 1, 2, 0), -- Needs dmg stacks

    -- He Bo
    ('wave Bender', 1, 3, 0),
    ('River Flow', 1, 5, 0),
    ('Rise of Tides', 1, 15, 0);

INSERT INTO god
    (name, category, element_id, health)
VALUES
    ('Achilles', 'Greek', 1, 100),
    ('Ares', 'Greek', 1, 100),
    ('Atlas', 'Greek', 1, 100),
    ('Apollo', 'Greek', 1, 100),

    ('Anhur', 'Egyptian', 1, 100),
    ('Anubis', 'Egyptian', 1, 100),
    ('Sobek', 'Egyptian', 1, 100),
    ('Horus', 'Egyptian', 1, 100),

    ('Odin', 'Norse', 1, 100),
    ('Thor', 'Norse', 1, 100),
    ('Tyr', 'Norse', 1, 100),
    ('Ullr', 'Norse', 1, 100),

    ('Ao Kuang', 'Chinese', 1, 100),
    ('Erlang Shen', 'Chinese', 1, 100),
    ('Guan Yu', 'Chinese', 1, 100),
    ('He Bo', 'Chinese', 1, 100);

INSERT INTO god_attack
    (god_id, attack_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),

    (2, 1),
    (2, 2),
    (2, 4),

    (3, 5),
    (3, 6),
    (3, 7),

    (4, 8),
    (4, 9),
    (4, 10),

    (5, 11),
    (5, 12),
    (5, 13),

    (6, 14),
    (6, 15),
    (6, 16),

    (7, 17),
    (7, 18),
    (7, 19),

    (8, 20),
    (8, 21),
    (8, 22),

    (9, 23),
    (9, 24),
    (9, 25),

    (10, 26),
    (10, 27),
    (10, 28),

    (11, 29),
    (11, 30),
    (11, 31),

    (12, 32),
    (12, 33),
    (12, 34),

    (13, 35),
    (13, 36),
    (13, 37),

    (14, 38),
    (14, 39),
    (14, 40),

    (15, 41),
    (15, 42),
    (15, 43),

    (16, 44),
    (16, 45),
    (16, 46);