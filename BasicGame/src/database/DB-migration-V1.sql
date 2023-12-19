CREATE TABLE player (
    player_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    xp BIGINT,
    games_played BIGINT,
    games_won BIGINT
);

CREATE TABLE god (
    god_id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(255),
    element_id BIGINT,
    health BIGINT,
    image VARCHAR(255)
);

CREATE TABLE player_god (
    player_id BIGINT PRIMARY KEY,
    god_id BIGINT PRIMARY KEY,
    level SMALLINT
);

CREATE TABLE element (
    element_id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE attack (
    attack_id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    element_id BIGINT,
    base_damage BIGINT
);

CREATE TABLE god_attack (
    god_id BIGINT,
    attack_id BIGINT
)