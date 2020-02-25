CREATE TABLE IF NOT EXISTS `House`
(
    `house_id`          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `zip_code`          INTEGER                           NOT NULL,
    `resident_name`     TEXT COLLATE NOCASE,
    `party_affiliation` TEXT COLLATE NOCASE,
    `address`           TEXT COLLATE NOCASE,
    `date`              INTEGER
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_House_address` ON `House` (`address`);

CREATE UNIQUE INDEX IF NOT EXISTS `index_House_resident_name` ON `House` (`resident_name`);

CREATE INDEX IF NOT EXISTS `index_House_zip_code` ON `House` (`zip_code`);

CREATE TABLE IF NOT EXISTS `Voter`
(
    `voter_id`         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `house_id`         INTEGER                           NOT NULL,
    `coordinates`      REAL                              NOT NULL,
    `time_stamp`       INTEGER,
    `name`             TEXT COLLATE NOCASE,
    `political_agenda` TEXT COLLATE NOCASE,
    `questions`        TEXT COLLATE NOCASE,
    `support`          INTEGER                           NOT NULL,
    `dog`              INTEGER                           NOT NULL,
    `soliciting_sign`  INTEGER                           NOT NULL,
    FOREIGN KEY (`house_id`) REFERENCES `House` (`house_id`) ON UPDATE NO ACTION ON DELETE RESTRICT
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Voter_name` ON `Voter` (`name`);

CREATE INDEX IF NOT EXISTS `index_Voter_house_id` ON `Voter` (`house_id`);

CREATE INDEX IF NOT EXISTS `index_Voter_time_stamp` ON `Voter` (`time_stamp`);

CREATE INDEX IF NOT EXISTS `index_Voter_support` ON `Voter` (`support`);

CREATE INDEX IF NOT EXISTS `index_Voter_dog` ON `Voter` (`dog`);

CREATE INDEX IF NOT EXISTS `index_Voter_soliciting_sign` ON `Voter` (`soliciting_sign`);


