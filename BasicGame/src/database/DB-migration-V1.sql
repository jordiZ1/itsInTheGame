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
    healing BIGINT NOT NULL,
    sound_file_name VARCHAR(255) NOT NULL
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
    (name, element_id, base_damage, healing, sound_file_name)
VALUES
    -- Achilles
    ('Spear Throw', 1, 5, 0,'SOspearSound.wav'),
    ('Shield Bash', 1, 3, 0,'SOshieldBash.wav'),
    ('Scorpion Slash', 1, 10, 0,'SOscorpionSlash.wav' ),

    -- Ares
    -- Spear Throw
    -- Shield Bash
    ('Battle Call', 1, 8, 0,'SObattleCall.wav' ),

    -- Atlas
    ('Shoulder Press', 1, 3, 0,'SOelbowPunch.wav'),
    ('Cloud Blow', 1, 6, 0,'SOkick.wav'),
    ('Star Shower', 1, 12, 0,'SOstarPunch.wav'),

    -- Apollo
    ('Arrow of Agreus', 1, 4, 0,'SOarrowOfAgreus.wav'),
    ('Symphony of Strings', 1, 7, 0,'SOsymphonyOfStrings.wav'),
    ('Healing Truth', 1, 0, 15,'SOhealingTruth.wav'),

    -- Anhur
    ('Battle Roar', 1, 3, 0,'SObattleRoar.wav'),
    ('Rise of Sand', 1, 6, 0,'SOriseOfSand.wav'),
    ('Desert Tsunami', 1, 10, 0,'SOdesertTsunami.wav'), -- Needs dmg buff

    -- Anubis
    ('Grave Digger', 1, 5, 0,'SOgraveDigger.wav'),
    ('Harvest', 1, 4, 0,'SOharvest.wav'),
    ('Hellraiser', 1, 12, 0,'SOhellraiser.wav'),

    -- Sobek
    ('March of Pharos', 1, 7, 0,'SOmarchOfPharaohs.wav'),
    ('Bite of the Nile', 1, 3, 0,'SObiteOfNile.wav'),
    ('Desert Tsunami', 1, 9, 0,'SOdesertTsunami.wav'),

    -- Horus
    ('Skyfall', 1, 5, 0,'SOskyfall.wav'),
    ('Light of the Throne', 1, 0, 5,'SOlightOfTheThrone.wav'),
    ('Sky Splitter', 1, 20, 0,'SOskySplitter.wav'),

    -- Odin
    ('Raven Attack', 1, 3, 0,'SOravenAttack.wav'),
    ('Staff of Death', 1, 6, 0,'SOstaffOfDeath.wav'),
    ('Tree of Wisdom', 1, 30, -30,'SOtreeOfWisdom.wav'),

    -- Thor
    ('Hammer Fall', 1, 5, 0,'SOhammerFall.wav'),
    ('Lightning of Gods', 1, 8, 0,'SOlightningOfTheGods.wav'),
    ('Hammer Storm', 1, 12, 0,'SOhammerStorm.wav'),
    -- Tyr
    ('Battle Cry', 1, 4, 0,'SObattleCry.wav'),
    ('Sun Eater', 1, 3, 0,'SOwaveBender.wav'), -- Needs dmg stacks
    ('Sword of Victory', 1, 5, 0,'SOswordOfVictory.wav'), -- Needs stack gaining

    -- Ullr
    ('Swift Arrow', 1, 4, 0,'SOswiftArrow.wav'),
    ('Freezing Axe', 1, 8, 0,'SOfreezingAxe.wav'),
    ('Fury of Snow', 1, 15, 0,'SOfuryOfSnow.wav'),

    -- Ao Kuang
    ('Dragon Breath', 1, 5, 0,'SOdragonBreath.wav'),
    ('Drought Storm', 1, 8, 0,'SOdroughtStorm.wav'),
    ('Ruler of Dragons', 1, 12, 0,'SOspearSound.wav'),

    -- Erlang Shen
    ('Spear of Strength', 1, 3, 0,'SOspearOfStrength.wav'),
    ('Tiger Slayer', 1, 7, 0,'SOTigerSlayer.wav'),
    ('Duel to the Death', 1, 10, 0,'SOduelToTheDeath.wavv'),

    -- Guan Yu
    ('Cavalry', 1, 0, 1,'SOcavalry.wav'), -- Needs stack gaining
    ('Assault', 1, 8, 0,'SOassault.wav'),
    ('Battle Charge', 1, 2, 0,'SObattleCharge.wav'), -- Needs dmg stacks

    -- He Bo
    ('wave Bender', 1, 3, 0,'SOwaveBender.wav'),
    ('River Flow', 1, 5, 0,'SOriverFlow.wav'),
    ('Rise of Tides', 1, 15, 0,'SOriseOfTides.wav');

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