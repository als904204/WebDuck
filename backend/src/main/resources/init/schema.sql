CREATE DATABASE IF NOT EXISTS webduck;

USE webduck;

CREATE TABLE IF NOT EXISTS member
(

    id                BIGINT(20)                                 NOT NULL AUTO_INCREMENT PRIMARY KEY,

    previous_login_at DATETIME(6),
    current_login_at  DATETIME(6),
    username          VARCHAR(16)                                NOT NULL,
    email             VARCHAR(255)                               NOT NULL UNIQUE,
    social_id         VARCHAR(255)                               NOT NULL,
    social_pk         VARCHAR(255)                               NOT NULL,
    role              ENUM ('ADMIN', 'MANAGER', 'USER')          NOT NULL,
    social_type       ENUM ('GOOGLE', 'KAKAO', 'NAVER', 'APPLE') NOT NULL,
    created_at        DATETIME(6),
    updated_at        DATETIME(6)
);


CREATE TABLE IF NOT EXISTS webtoon
(
    review_count        INT(11)       NOT NULL,
    id                  BIGINT(20)    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    author              VARCHAR(60)   NOT NULL,
    title               VARCHAR(40)   NOT NULL,
    image_path          VARCHAR(255)  NOT NULL,
    original_image_name VARCHAR(255)  NOT NULL,
    summary             VARCHAR(4000) NOT NULL,
    webtoon_url         VARCHAR(255),
    platform            VARCHAR(255)  NOT NULL,
    publish_day         VARCHAR(255)  NOT NULL,
    created_at          DATETIME(6),
    updated_at          DATETIME(6)
);




desc webtoon;
CREATE TABLE IF NOT EXISTS genre
(
    id         BIGINT(20)  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(20) NOT NULL UNIQUE,
    created_at DATETIME(6),
    updated_at DATETIME(6)
);



CREATE TABLE IF NOT EXISTS webtoon_genre
(
    genre_id   BIGINT(20),
    id         BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    webtoon_id BIGINT(20),
    FOREIGN KEY (genre_id) REFERENCES genre (id),
    FOREIGN KEY (webtoon_id) REFERENCES webtoon (id),
    created_at DATETIME(6),
    updated_at DATETIME(6)
);


CREATE TABLE IF NOT EXISTS review
(
    likes_count       INT(11)      NOT NULL,
    rating            INT(11)      NOT NULL,
    created_at        DATETIME(6),
    id                BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id         BIGINT(20)   NOT NULL,
    updated_at        DATETIME(6),
    webtoon_id        BIGINT(20)   NOT NULL,
    content           VARCHAR(255) NOT NULL,
    reviewer_nickname VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (webtoon_id) REFERENCES webtoon (id)
);


CREATE TABLE IF NOT EXISTS review_likes
(
    id         BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT(20),
    review_id  BIGINT(20),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (review_id) REFERENCES review (id)
);

CREATE TABLE IF NOT EXISTS collection
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(20)  NOT NULL CHECK (CHAR_LENGTH(title) BETWEEN 2 AND 20),
    description VARCHAR(200) NOT NULL CHECK (CHAR_LENGTH(description) BETWEEN 2 AND 200),
    owner_id    BIGINT       NOT NULL,
    owner_name  VARCHAR(255) NOT NULL,
    is_owner    TINYINT(1)   NOT NULL DEFAULT 0,
    created_at  DATETIME(6),
    updated_at  DATETIME(6),
    FOREIGN KEY (owner_id) REFERENCES member (id)
);



CREATE TABLE IF NOT EXISTS `collection_webtoons`
(
    id              BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `owner_id`      BIGINT,
    `webtoon_id`    BIGINT,
    `collection_id` BIGINT,
    created_at      DATETIME(6),
    updated_at      DATETIME(6),
    FOREIGN KEY (`owner_id`) REFERENCES `member` (`id`),
    FOREIGN KEY (`webtoon_id`) REFERENCES `webtoon` (`id`),
    FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`)
);

CREATE TABLE IF NOT EXISTS external_api_request_log
(
    id                  BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    last_requested_page INTEGER      NOT NULL,
    platform            VARCHAR(255) NOT NULL
);

