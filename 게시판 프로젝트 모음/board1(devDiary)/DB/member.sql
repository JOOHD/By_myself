CREATE TABLE `members` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,                      -- 겹처짐 방지 구분자 PK
	`userId` VARCHAR(64) NULL DEFAULT NULL,                    -- 유저 아이디
	`password` VARCHAR(256) NULL DEFAULT NULL,                 -- 유저 비밀번호
	`nickname` VARCHAR(64) NULL DEFAULT NULL,                  -- 유저 닉네임
	`email` VARCHAR(64) NULL DEFAULT NULL,                     -- 유저 이메일
	`authority` INT(11) NULL DEFAULT NULL,                     -- 권한(관리자, 사용자 등...)
	`declaration` INT(11) NULL DEFAULT NULL,                   -- 신고 횟수
	`last_login` TIMESTAMP NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`create_time` TIMESTAMP NULL DEFAULT '0000-00-00 00:00:00',     -- 유저 생성한 시간(회원가입 시간)
	`update_time` TIMESTAMP NULL DEFAULT '0000-00-00 00:00:00',     -- 비밀번호 업데이트 시간(n일마다 교체 요구 기능 만들 예정)
	PRIMARY KEY (`id`)
    UNIQUE INDEX `userId` (`userId`)                           -- 웹 로직을 피해 DB에 데이터를 넣을 경우라도 중복은 피할 수 있게 유니크 키를 넣어준다.
)