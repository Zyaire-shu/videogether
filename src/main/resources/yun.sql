CREATE TABLE yunshu.`User` (
                               UserId INT auto_increment NOT NULL primary key,
                               UserName varchar(100) NOT NULL,
                               PassWord varchar(100) NOT NULL,
                               Auth varchar(10) DEFAULT 'guest' NOT NULL
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci;

CREATE TABLE yunshu.WatchRecord (
                                    id INT auto_increment NOT NULL primary key,
                                    `path` TEXT NOT NULL,
                                    `time` DATETIME NULL,
                                    `user` varchar(100) NOT NULL
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci;
