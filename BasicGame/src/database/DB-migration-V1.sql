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
    element VARCHAR(255) NOT NULL,
    health BIGINT NOT NULL
);

CREATE TABLE player_god (
    player_id BIGINT CONSTRAINT fk_player REFERENCES player(player_id),
    god_id BIGINT CONSTRAINT fk_god REFERENCES god(god_id),
    level SMALLINT NOT NULL DEFAULT 1,
    PRIMARY KEY (player_id, god_id)
);

CREATE TABLE attack (
    attack_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    element VARCHAR(255) NOT NULL,
    base_damage BIGINT NOT NULL,
    healing BIGINT NOT NULL,
    sound_file_name VARCHAR(255) NOT NULL
);

CREATE TABLE god_attack (
    god_id BIGINT NOT NULL CONSTRAINT fk_god REFERENCES god(god_id),
    attack_id BIGINT NOT NULL CONSTRAINT fk_attack REFERENCES attack(attack_id),
    PRIMARY KEY (god_id, attack_id)
);

INSERT INTO player
    (name)
VALUES
    ('player1'),
    ('player2'),
    ('player3'),
    ('player4');

INSERT INTO attack
    (name, element_id, base_damage, healing, sound_file_name)
VALUES
    -- Achilles
    ('Spear Throw', 'fire', 5, 0,'SOspearSound.wav'),
    ('Shield Bash', 'earth', 3, 0,'SOshieldBash.wav'),
    ('Scorpion Slash', 'earth', 10, 0,'SOscorpionSlash.wav' ),

    -- Ares
    -- Spear Throw
    -- Shield Bash
    ('Battle Call', 'earth', 8, 0,'SObattleCall.wav' ),

    -- Atlas
    ('Shoulder Press', 'basic', 3, 0,'SOelbowPunch.wav'),
    ('Cloud Blow', 'earth', 6, 0,'SOkick.wav'),
    ('Star Shower', 'light', 12, 0,'SOstarPunch.wav'),

    -- Apollo
    ('Arrow of Agreus', 'basic', 4, 0,'SOarrowOfAgreus.wav'),
    ('Symphony of Strings', 'light', 7, 0,'SOsymphonyOfStrings.wav'),
    ('Healing Truth', 'basic', 0, 15,'SOhealingTruth.wav'),

    -- Anhur
    ('Battle Roar', 'basic', 3, 0,'SObattleRoar.wav'),
    ('Rise of Sand', 'earth', 6, 0,'SOriseOfSand.wav'),
    ('Desert Tsunami', 'earth', 10, 0,'SOdesertTsunami.wav'), -- Needs dmg buff

    -- Anubis
    ('Grave Digger', 'earth', 5, 0,'SOgraveDigger.wav'),
    ('Harvest', 'darkness', 4, 0,'SOharvest.wav'),
    ('Hellraiser', 'fire', 12, 0,'SOhellraiser.wav'),

    -- Sobek
    ('March of Pharos', 'earth', 7, 0,'SOmarchOfPharaohs.wav'),
    ('Bite of the Nile', 'water', 3, 0,'SObiteOfNile.wav'),
    ('Desert Tsunami', 'earth', 9, 0,'SOdesertTsunami.wav'),

    -- Horus
    ('Skyfall', 'light', 5, 0,'SOskyfall.wav'),
    ('Light of the Throne', 'light', 0, 5,'SOlightOfTheThrone.wav'),
    ('Sky Splitter', 'earth', 20, 0,'SOskySplitter.wav'),

    -- Odin
    ('Raven Attack', 'darkness', 3, 0,'SOravenAttack.wav'),
    ('Staff of Death', 'earth', 6, 0,'SOstaffOfDeath.wav'),
    ('Tree of Wisdom', 'earth', 30, -30,'SOtreeOfWisdom.wav'),

    -- Thor
    ('Hammer Fall', 'basic', 5, 0,'SOhammerFall.wav'),
    ('Lightning of Gods', 'light', 8, 0,'SOlightningOfTheGods.wav'),
    ('Hammer Storm', 'light', 12, 0,'SOhammerStorm.wav'),
    
    -- Tyr
    ('Battle Cry', 'basic', 4, 0,'SObattleCry.wav'),
    ('Sun Eater', 'fire', 3, 0,'SOwaveBender.wav'), -- Needs dmg stacks
    ('Sword of Victory', 'fire', 5, 0,'SOswordOfVictory.wav'), -- Needs stack gaining

    -- Ullr
    ('Swift Arrow', 'basic', 4, 0,'SOswiftArrow.wav'),
    ('Freezing Axe', 'water', 8, 0,'SOfreezingAxe.wav'),
    ('Fury of Snow', 'earth', 15, 0,'SOfuryOfSnow.wav'),

    -- Ao Kuang
    ('Dragon Breath', 'fire', 5, 0,'SOdragonBreath.wav'),
    ('Drought Storm', 'fire', 8, 0,'SOdroughtStorm.wav'),
    ('Ruler of Dragons', 'fire', 12, 0,'SOspearSound.wav'),

    -- Erlang Shen
    ('Spear of Strength', 'basic', 3, 0,'SOspearOfStrength.wav'),
    ('Tiger Slayer', 'earth', 7, 0,'SOTigerSlayer.wav'),
    ('Duel to the Death', 'earth', 10, 0,'SOduelToTheDeath.wavv'),

    -- Guan Yu
    ('Cavalry', 'basic', 0, 1,'SOcavalry.wav'), -- Needs stack gaining
    ('Assault', 'light', 8, 0,'SOassault.wav'),
    ('Battle Charge', 'light', 2, 0,'SObattleCharge.wav'), -- Needs dmg stacks

    -- He Bo
    ('wave Bender', 'water', 3, 0,'SOwaveBender.wav'),
    ('River Flow', 'water', 5, 0,'SOriverFlow.wav'),
    ('Rise of Tides', 'water', 15, 0,'SOriseOfTides.wav');

INSERT INTO god
    (name, category, element, health)
VALUES
    ('Achilles', 'Greek', 'fire', 100),
    ('Ares', 'Greek', 'earth', 100),
    ('Atlas', 'Greek', 'light', 100),
    ('Apollo', 'Greek', 'light', 100),

    ('Anhur', 'Egyptian', 'earth', 100),
    ('Anubis', 'Egyptian', 'darkness', 100),
    ('Sobek', 'Egyptian', 'water', 100),
    ('Horus', 'Egyptian', 'light', 100),

    ('Odin', 'Norse', 'earth', 100),
    ('Thor', 'Norse', 'light', 100),
    ('Tyr', 'Norse', 'fire', 100),
    ('Ullr', 'Norse', 'water', 100),

    ('Ao Kuang', 'Chinese', 'fire', 100),
    ('Erlang Shen', 'Chinese', 'earth', 100),
    ('Guan Yu', 'Chinese', 'light', 100),
    ('He Bo', 'Chinese', 'water', 100);

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