CREATE TABLE `user` (
	`id`	BIGINT	NOT NULL,
	`email`	VARCHAR(100)	NULL,
	`name`	VARCHAR(100)	NULL,
	`created_at`	DATETIME	NULL,
	`updated_at`	DATETIME	NULL
);

CREATE TABLE `schedule` (
	`id`	BIGINT	NOT NULL,
	`userId`	BIGINT	NOT NULL,
	`taskTitle`	VARCHAR(100)	NULL,
	`taskContents`	VARCHAR(100)	NULL,
	`password`	VARCHAR(255)	NULL,
	`created_at`	DATETIME	NULL,
	`updated_at`	DATETIME	NULL
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`id`
);

ALTER TABLE `schedule` ADD CONSTRAINT `PK_SCHEDULE` PRIMARY KEY (
	`id`
);

ALTER TABLE `schedule` ADD CONSTRAINT `FK_user_TO_schedule_1` FOREIGN KEY (
	`userId`
)
REFERENCES `user` (
	`id`
);

