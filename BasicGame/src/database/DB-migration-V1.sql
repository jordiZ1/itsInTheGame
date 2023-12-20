CREATE TABLE player (
    player_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    xp BIGINT NOT NULL,
    games_played BIGINT,
    games_won BIGINT
);

CREATE TABLE god (
    god_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    element_id BIGINT NOT NULL,
    health BIGINT NOT NULL,
    image VARCHAR(255) NOT NULL
);

CREATE TABLE player_god (
    player_id BIGINT PRIMARY KEY,
    god_id BIGINT PRIMARY KEY,
    level SMALLINT
);

CREATE TABLE element (
    element_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE attack (
    attack_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    element_id BIGINT,
    base_damage BIGINT NOT NULL
);

CREATE TABLE god_attack (
    god_id BIGINT,
    attack_id BIGINT
)