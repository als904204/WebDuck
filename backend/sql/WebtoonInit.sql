USE webduck;

CREATE TABLE IF NOT EXISTS member (
                                      created_at DATETIME(6),
                                      id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      updated_at DATETIME(6),
                                      previous_login_at DATETIME(6),
                                      current_login_at DATETIME(6),
                                      username VARCHAR(16) NOT NULL,
                                      email VARCHAR(255) NOT NULL UNIQUE,
                                      social_id VARCHAR(255) NOT NULL,
                                      social_pk VARCHAR(255) NOT NULL,
                                      role ENUM('ADMIN', 'MANAGER', 'USER') NOT NULL,
                                      social_type ENUM('GOOGLE', 'KAKAO', 'NAVER', 'APPLE') NOT NULL
);

CREATE TABLE IF NOT EXISTS webtoon (
                                       review_count INT(11) NOT NULL,
                                       id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       author VARCHAR(30) NOT NULL,
                                       title VARCHAR(40) NOT NULL,
                                       image_path VARCHAR(255) NOT NULL,
                                       original_image_name VARCHAR(255) NOT NULL,
                                       summary VARCHAR(600) NOT NULL,
                                       webtoon_url VARCHAR(255),
                                       platform ENUM('NAVER', 'KAKAO', 'TOPTOON', 'ELSE') NOT NULL,
                                       publish_day ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL
);

CREATE TABLE IF NOT EXISTS genre (
                                     id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS webtoon_genre (
                                             genre_id BIGINT(20),
                                             id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                             webtoon_id BIGINT(20),
                                             FOREIGN KEY (genre_id) REFERENCES genre(id),
                                             FOREIGN KEY (webtoon_id) REFERENCES webtoon (id)
);

CREATE TABLE IF NOT EXISTS review (
                                      likes_count INT(11) NOT NULL,
                                      rating INT(11) NOT NULL,
                                      created_at DATETIME(6),
                                      id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      member_id BIGINT(20) NOT NULL,
                                      updated_at DATETIME(6),
                                      webtoon_id BIGINT(20) NOT NULL,
                                      content VARCHAR(255) NOT NULL,
                                      reviewer_nickname VARCHAR(255) NOT NULL,
                                      FOREIGN KEY (member_id) REFERENCES member(id),
                                      FOREIGN KEY (webtoon_id) REFERENCES webtoon(id)
);

CREATE TABLE IF NOT EXISTS review_likes (
                                            id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                            member_id BIGINT(20),
                                            review_id BIGINT(20),
                                            FOREIGN KEY (member_id) REFERENCES member(id),
                                            FOREIGN KEY (review_id) REFERENCES review(id)
);


INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('치즈', 'https://image-comic.pstatic.net/webtoon/733074/thumbnail/thumbnail_IMAG21_80df3e76-47af-4007-b57c-e8f2830835e5.jpg', '백수세끼.jpg', 'NAVER', 'MONDAY', '백수 시절 내 곁을 지켜줬던 그녀... 돌아와 주면 안 되겠니?음식 메뉴마다 담겨 있는 우리들의 연애 흑역사!', '백수세끼','https://comic.naver.com/webtoon/list?titleId=733074',0);

INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'DAILYLIFE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'GAG'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'EMOTION'), LAST_INSERT_ID());

INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('SIU', 'https://image-comic.pstatic.net/webtoon/183559/thumbnail/thumbnail_IMAG21_5f3fec31-5c95-4afe-a73f-3046288edb47.jpg', '신의 탑.jpg', 'NAVER', 'MONDAY', '자신의 모든 것이었던 소녀를 쫓아 탑에 들어온 소년 그리고 그런 소년을 시험하는 탑', '신의 탑','https://comic.naver.com/webtoon/list?titleId=183559',0);

INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'ACTION'), LAST_INSERT_ID());

INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('박태준 만화회사, 유누니 / 박태준 만화회사, 태완', 'https://image-comic.pstatic.net/webtoon/783052/thumbnail/thumbnail_IMAG21_800f4c56-26ac-419e-9ed0-baf322311dea.jpg', '퀘스트지상주의.jpg', 'NAVER', 'MONDAY', '[외모지상주의], [싸움독학], [인생존망]과 세계관을 공유하는 작품!
공부, 싸움, 외모.
뭐 하나 잘난 것 없는 평범한 고등학생 ‘김수현’의 눈앞에 갑자기 퀘스트창이 나타났다!
첫 번째 퀘스트, [엄마한테 사과하기]를 완료한 수현은 보상으로 키가 3cm 커지게 되는데!
퀘스트 엄청 쉽잖아?! 어떤 퀘스트든 완료해주마! 그런데 다음 퀘스트가?
[system] 학교 일진녀와 키스하십시오', '퀘스트지상주의','https://comic.naver.com/webtoon/list?titleId=783052',0);

INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'DRAMA'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'DAILYLIFE'), LAST_INSERT_ID());

INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('김인호 / 조형근', 'https://image-comic.pstatic.net/webtoon/728750/thumbnail/thumbnail_IMAG21_47c21251-b213-4882-bacc-15adce1acfc8.jpg', '장씨세가 호위무사.jpg', 'NAVER', 'MONDAY', '‘당신이 부른 것이오. 나란 사람을... ’
은둔고수 광휘. 호위무사 되다.
웹소설 원작 웰메이드 무협 시대극!', '장씨세가 호위무사','https://comic.naver.com/webtoon/list?titleId=728750',0);

INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ACTION'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'MARTIALARTS'), LAST_INSERT_ID());


INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('조용석', 'https://image-comic.pstatic.net/webtoon/602910/thumbnail/thumbnail_IMAG21_e861f2cf-6157-4d33-8e02-7b4cbf0a8baf.jpg', '윈드브레이커.jpg', 'NAVER', 'MONDAY', '혼자서 자전거를 즐겨타던 모범생 조자현.
원치 않게 자전거 크루의 일에 자꾸 휘말리게 되는데...
자유를 꿈꾸는 청춘들의 스트릿라이딩 드라마!', '윈드브레이커','https://comic.naver.com/webtoon/list?titleId=602910',0);

INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'SPORTS'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'DRAMA'), LAST_INSERT_ID());






INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('조석', 'https://image-comic.pstatic.net/webtoon/814543/thumbnail/thumbnail_IMAG21_df84a681-b7ef-4dda-8cef-25b219d35e3e.jpg', '마음의소리2.jpg', 'NAVER', 'TUESDAY', '''마음의소리'' 완결 후 3년, 조석 작가의 결코 소소하지 않은 일상(?) 대공개!
제목은... ''너는 그냥 개그만화나 그려라''
(였는데 다들 ''마음의소리2''라 불러서 제목 바꿈)', '마음의소리2','https://comic.naver.com/webtoon/list?titleId=814543',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'GAG')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE'));

INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('박태준 만화회사 / 정종택', 'https://image-comic.pstatic.net/webtoon/783053/thumbnail/thumbnail_IMAG21_d7732f14-26da-4e35-8762-660cc87b53f1.jpg', '김부장.jpg', 'NAVER', 'TUESDAY', '“제발 안경 쓴 아저씨는 건들지 말자…”
오직 자신의 딸 ''민지''를 위해 특수요원직을 관두고 평범함을 선택한 가장 김부장.
그러던 어느 날 민지가 소리소문 없이 사라지고, 김부장은 자신을 감시하는 국가를 적으로 돌리면서까지 딸을 찾아나서기 시작하는데...
[외모지상주의], [싸움독학] 그리고 [인생존망]의 세계관을 잇는 공식 스핀오프 작품!', '김부장','https://comic.naver.com/webtoon/list?titleId=783053',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));




INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('모죠', 'https://image-comic.pstatic.net/webtoon/796152/thumbnail/thumbnail_IMAG21_26b9c1d8-ca2d-4fc7-87ea-a3334634236a.jpg', '마루는 강쥐.jpg', 'NAVER', 'TUESDAY', '우리 집 강아지 마루가 사람이 되었다, 그것도 5살 아이로!
강아지 + 어린아이의 무한 에너지와 호기심을 지닌 사고뭉치 강쥐 탄생!
마루야~! 또 어디가!!! 유쾌한 이웃들과 우당탕탕 즐거운 마루의 나날들', '마루는 강쥐','https://comic.naver.com/webtoon/list?titleId=796152',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE'));

INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('언데드딸기 / 언데드감자 / 싱숑', 'https://image-comic.pstatic.net/webtoon/789979/thumbnail/thumbnail_IMAG21_792cd36e-d722-4970-933b-3da7c37ee812.jpg', '멸망 이후의 세계.jpg', 'NAVER', 'TUESDAY', '<전지적 독자 시점> 싱숑 작가 원작.

이것은 모두가 과거로 돌아갈 때 마지막까지 회귀하지 않았던 한 사나이의 이야기다.', '멸망 이후의 세계','https://comic.naver.com/webtoon/list?titleId=789979',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'THRILLER'));



INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('지발', 'https://image-comic.pstatic.net/webtoon/811721/thumbnail/thumbnail_IMAG21_9a2a959a-666b-4156-8e4f-db64dfe319c6.jpg', '무직백수 계백순.jpg', 'NAVER', 'WEDNESDAY', '해둔 것도 없고, 스펙도 평범, 통장 잔고는 늘 만원 이하.
등골 브레이커 계백순은 과연 백수에서 탈출할 수 있을까?', '무직백수 계백순','https://comic.naver.com/webtoon/list?titleId=811721',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));




-- 목 STRAT
-- 선천적 얼간이들
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('가스파드', 'https://image-comic.pstatic.net/webtoon/478261/thumbnail/thumbnail_IMAG21_7fbd8610-0a97-40e3-9c63-101ea07fc4b4.jpg', '선천적 얼간이들.jpg', 'NAVER', 'THURSDAY', '뭘 해도 안되는 얼간이 신인류가 떴다!
낙천적 우유부단 거북이 가스파드와 말초적 친구들의 좌충우돌 라이프.
이미 화제의 중심! ''Natural Born Idiots''의 새로운 이름, ''선천적 얼간이들''', '선천적 얼간이들','https://comic.naver.com/webtoon/list?titleId=478261',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE'));


-- 나노마신
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('현절무, 금강불괴, 한중월야', 'https://image-comic.pstatic.net/webtoon/747271/thumbnail/thumbnail_IMAG21_75c6a3cc-c4d1-4b51-a945-029aff772a63.jpg', '나노마신.jpg', 'NAVER', 'THURSDAY', '갖은 멸시와 목숨의 위협을 받던 마교의 사생아 천여운,
미래에서 나타난 후손이 천여운의 몸에 나노 머신을 주입한 후 그의 인생이 송두리째 바뀐다.
마교를 넘어 무림 최고의 자리에 오르기 위한 천여운의 거침없는 이야기가 시작된다!', '나노마신','https://comic.naver.com/search?keyword=%EB%82%98%EB%85%B8',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 촉법소년
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('박태준 만화회사, 남자의 이야기 / 정종택', 'https://image-comic.pstatic.net/webtoon/808198/thumbnail/thumbnail_IMAG21_2aabb538-5b1a-4d74-81ed-b41bb8a155ff.jpg', '촉법소년.jpg', 'NAVER', 'THURSDAY', '대한민국은 X같은 법들로 가득하다
나를 지옥으로 밀어넣고도 처벌받지 않았던 ‘촉법소년’들
그 X같은 법의 결과물들에게, 지금부터 복수를 시작한다', '촉법소년','https://comic.naver.com/webtoon/list?titleId=808198',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 재벌집 막내아들
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('JP / 김병관 / 산경(山景)', 'https://image-comic.pstatic.net/webtoon/800770/thumbnail/thumbnail_IMAG21_d2e1e7ee-fc83-4030-a1e7-200378bc088f.jpg', '재벌집 막내아들.jpg', 'NAVER', 'THURSDAY', '[화제의 드라마 원작 웹툰!]
13년 동안의 수고를 배신으로 돌려받다니!
머슴처럼 살다 버려진 순양그룹의 실장, 윤현우.
모든 게 끝났다 싶었을 즘, 순양그룹 진 회장의 손주 진도준으로 깨어난다!
하지만 상속권과는 거리가 먼 재벌집 막내아들 진도준.
과연 그는 가족 정치극에서 최후의 승자가 될 수 있을 것인가!', '재벌집 막내아들','https://comic.naver.com/webtoon/list?titleId=800770',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('운, 배민기', 'https://image-comic.pstatic.net/webtoon/746857/thumbnail/thumbnail_IMAG21_fb7d7fcd-19f1-49eb-9cc8-aae9622cdd04.jpg', '무사만리행.jpg', 'NAVER', 'THURSDAY', '2세기 후반, 마한연맹 고리국(古離國)의 무사 나루.
믿었던 스승의 배신으로 나라가 멸망하고, 평생을 바쳐 지켜주겠노라 맹세했던 소단공주의 행방이 묘연해진다.
유일한 단서인 서역으로 떠났다는 배신자의 말에 나루는 로마제국의 검투 노예로 팔려가 공주의 행방을 찾고 자유를 얻기 위해 처절한 사투를 벌인다.', '무사만리행','https://comic.naver.com/webtoon/list?titleId=746857',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'MARTIALARTS'));
-- 목 END


-- 금 START
-- 외모지상주의
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('박태준', 'https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG21_01672165-03c8-44b1-ba0e-ef82c9cfcd10.jpg', '외모지상주의.jpg', 'NAVER', 'FRIDAY', '못생기고 뚱뚱하다고 괴롭힘을 당하며 루저 인생만 살아온 내가 잘생겨졌다는 이유로 인싸가 됐다.

어느 날 자고 일어났더니 갑자기 완벽한 외모와 몸을 지닌 사람이 되어 깨어난다면?', '외모지상주의','https://comic.naver.com/webtoon/list?titleId=641253',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 역대급 영지 설계사
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('이현민, 김현수, 문백경', 'https://image-comic.pstatic.net/webtoon/777767/thumbnail/thumbnail_IMAG21_cc85f891-272b-450a-b642-cffe1568ab71.jpg', '역대급 영지 설계사.jpg', 'NAVER', 'FRIDAY', '소설 속 귀족이 된 토목공학도 김수호.
그런데 뭐? 내 영지가 곧 망할 거라고?
그럼 살려야지. 설계하고, 건설하고, 분양해서.
[전 대륙이 기다려온 특별한 기회! 퍼펙트한 교통, 최상의 학군, 쾌적한 숲세권, 원스톱 프리미엄 영지 라이프의 프론테라 남작령이 여러분을 기다립니다. 선착순 분양계약중!]', '역대급 영지 설계사','https://comic.naver.com/webtoon/list?titleId=777767',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));


-- 상남자
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('하늘소 / 도가도 / 김태궁', 'https://image-comic.pstatic.net/webtoon/751168/thumbnail/thumbnail_IMAG21_529c6125-cf14-435f-94ed-db1a2d499d84.jpg', '상남자.jpg', 'NAVER', 'FRIDAY', '오로지 성공만을 바라보며 기업 정점의 자리까지 오른, 최고의 샐러리맨 한유현.
대가로 사랑하던 모든 이를 잃어버린 후, 후회 섞인 인생을 돌아보는데...
어느 날 모든 기억을 안은 채, 젊은 시절의 그때로 돌아가게 된다.
모든 것의 시작점인 신입사원의 위치에서, 그가 다시 삶을 설계한다!', '상남자','https://comic.naver.com/webtoon/list?titleId=751168',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 금 END



-- 토 START

-- 마도전생기
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('포스스튜디오 / codezero', 'https://image-comic.pstatic.net/webtoon/807777/thumbnail/thumbnail_IMAG21_e47489ea-bf97-4726-a8e7-6403092a303a.jpg', '마도전생기.jpg', 'NAVER', 'SATURDAY', '정파무림 의천맹의 최강 살수, 천하진.

자유를 찾기 위해 탈출을 감행하지만 코앞에서 목숨을 잃고 만다.

그런데…
''신교불패, 만마앙복. 삼공자님의 쾌유를 경하드리옵니다!''', '마도전생기','https://comic.naver.com/webtoon/list?titleId=807777',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'MARTIALARTS'));


-- 귀환했는데 입대 전날이다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('해일 / 오코믹 / 황금비둘기', 'https://image-comic.pstatic.net/webtoon/807393/thumbnail/thumbnail_IMAG21_aff8a206-014c-471f-9e91-c569940ad8e5.jpg', '귀환했는데 입대 전날이다.jpg', 'NAVER', 'SATURDAY', '대한민국의 평범한 고3이었던 김민준은 갑자기 이세계로 소환 당해 흑마법사가 되었다.
집으로 돌아가겠다는 일념으로 온갖 고난을 뚫고 흑마법으로 이세계를 구한 민준.
영웅으로서의 삶과 보장된 부귀영화를 뿌리치고 지구로 복귀한다.
그토록 기다리던 고향의 문물을 즐기려고 하던 차, 문제가 생겼다.
터져버린 던전, 쏟아지는 몬스터들이 이제 막 귀환한 민준의 안락한 지구 라이프를 위협하는 것도 모자라...
귀환 하루만에 입대를 하라고!?!!', '귀환했는데 입대 전날이다','https://comic.naver.com/webtoon/list?titleId=807393',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));

-- 토 END



-- 일 START
-- 싸움독학
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('박태준 만화회사 / 김정현 스튜디오', 'https://image-comic.pstatic.net/webtoon/736277/thumbnail/thumbnail_IMAG21_bbbe3f76-021e-4dbc-830a-4358c1abec0c.jpg', '싸움독학.jpg', 'NAVER', 'SUNDAY', '힘없고 가진거 하나 없이 맞고만 살던 나였는데...
우연히 비밀의 뉴튜브를 발견하게 되고 갑자기 떼돈을 벌었다.', '싸움독학','https://comic.naver.com/webtoon/list?titleId=736277',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 입학용병
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('YC / 락현', 'https://image-comic.pstatic.net/webtoon/758150/thumbnail/thumbnail_IMAG21_4135492154714961716.jpg', '입학용병.jpg', 'NAVER', 'SUNDAY', '어린 시절 비행기 추락 사고의 유일한 생존자 유이진.
살아남기 위해 용병으로 살아가던 유이진은 10년 후, 가족이 있는 고향으로 돌아왔다.', '입학용병','https://comic.naver.com/webtoon/list?titleId=758150',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'STORY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));


-- 나 혼자 특성빨로 무한 성장
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('샤이아탄 / 2사랑 / 선운(鮮雲)', 'https://image-comic.pstatic.net/webtoon/803909/thumbnail/thumbnail_IMAG21_764d377c-fe45-40f9-8775-0f6ff92ad421.jpg', '나 혼자 특성빨로 무한 성장.jpg', 'NAVER', 'SUNDAY', '평생 헌터가 되지 못할 줄 알았다.
기회 같은 것은 찾아오지 않을 거라 여겼다.
하지만 아니었다.

「‘시련의 탑’이 지구 차원의 모든 적합자를 선별해냈습니다.」', '나 혼자 특성빨로 무한 성장','https://comic.naver.com/webtoon/list?titleId=803909',0);

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));

-- 일 END


-- 카카오
-- 월 START

-- 철혈검가 사냥개의 회귀
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('이산책(REDICE STUDIO), 설아랑, 레고밟았어', 'https://dn-img-page.kakao.com/download/resource?kid=cWu2nJ/hzVqKMVQuF/hSeVf9wqXVZsal7kjHn9tk&filename=th3', '철혈검가 사냥개의 회귀.jpg', 'KAKAO', 'MONDAY', '바스커빌 가문의 사냥개 ''비키르''.

사냥개로서 가문에 충성한 보답은
모함과 누명으로 얼룩진 단두대의 칼날이었다.

"다시는 토끼를 잡고 나면
삶아지는 사냥개처럼 살지 않겠노라."

그런 그에게 죽음 대신 찾아온 뜻밖의 기회.

어둠 속에서 송곳니를 갈고닦는
비키르의  눈이 붉게 빛났다.

"기다려라. 휴고. 이번엔 반드시 네 목을 물어뜯어 주마."

주인을 향한 사냥개의 잔혹한 핏빛 복수가 시작된다.', '철혈검가 사냥개의 회귀','https://webtoon.kakao.com/content/%EC%B2%A0%ED%98%88%EA%B2%80%EA%B0%80-%EC%82%AC%EB%83%A5%EA%B0%9C%EC%9D%98-%ED%9A%8C%EA%B7%80/3455',0);

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 월 END


-- 화 START
-- 다정한 그대를 지키는 방법
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('하운드, 김지의, 마약젤리', 'https://dn-img-page.kakao.com/download/resource?kid=etke9/hAd4UDU7Ln/ZWFOMebkRm8woCaZoMtVA0&filename=th3', '다정한 그대를 지키는 방법.jpg', 'KAKAO', 'TUESDAY', '빌나를 증오하는 남자와 결혼하게 되었다. 나의 어머니가 그의 가족들을 죽였으니, 당연한 일이었다. “결혼 전에 꼭 약속해 주셨으면 하는 게 있어요 반년 후엔, 이혼해 주셨으면 해요.” “……네?” “억지로 하는 결혼이잖아요. 원치 않는 결혼을 굳이 이어 갈 필요 없다고 생각해요.” “원치 않는, 결혼이요.” 그는 멍하니 나의 말을 반복했다. [회귀/선결혼 후연애/능력녀/존대남/대형견 남주/약간의 착각계/치유계/쌍방구원/정통 로맨스 지향]', '다정한 그대를 지키는 방법','https://webtoon.kakao.com/content/%EB%8B%A4%EC%A0%95%ED%95%9C-%EA%B7%B8%EB%8C%80%EB%A5%BC-%EC%A7%80%ED%82%A4%EB%8A%94-%EB%B0%A9%EB%B2%95/2485',0);

-- 웹툰과 장르 연결 (로맨스, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());


-- 화 END


-- 수 START
-- 황제의 아이를 숨기는 방법
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('쌀, 이연선', 'https://dn-img-page.kakao.com/download/resource?kid=bk77pD/hzXarSM7qN/kl75PjbZNx3LYhWZMMuDDK&filename=th3', '황제의 아이를 숨기는 방법.jpg', 'KAKAO', 'WEDNESDAY', '"이혼해 줘, 아스텔. 어차피 당신도 날 사랑한 적 없잖아?" 황제 카이젠만을 바라보며 완벽한 황후가 되기 위해 노력했던 아스텔. 하지만 카이젠의 이혼 요구에 아스텔의 10년이 하룻밤만에 부서졌다. 다시는 황실과 얽히지 않으리라 다짐하며 가족과의 연까지 끊어내고, 시골로 몸을 숨기지만… "…임신, 하신 것 같습니다." 과연 아스텔은 카이젠과 위협 세력으로부터 황제의 아이, 테오르를 무사히 숨길 수 있을까?', '황제의 아이를 숨기는 방법','https://webtoon.kakao.com/content/%ED%99%A9%EC%A0%9C%EC%9D%98-%EC%95%84%EC%9D%B4%EB%A5%BC-%EC%88%A8%EA%B8%B0%EB%8A%94-%EB%B0%A9%EB%B2%95/2881',0);

-- 웹툰과 장르 연결 (로맨스, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 무한의 마법사
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('kiraz(REDICE STUDIO), 테미스, 김치우', 'https://dn-img-page.kakao.com/download/resource?kid=bbXpgU/hyoXzXU0Zn/y5Ae8SzxX1EorWXZH6Ez90&filename=th3', '무한의 마법사.jpg', 'KAKAO', 'WEDNESDAY', '마굿간에 버려져 평민으로 키워진 아이, 시로네. 타고난 통찰력으로 글까지 깨우친 아이는, 도시로 나간 어느 날,그토록 궁금해하던 마법을 경험한다. 그 길로 마법사를 꿈꾸게 된 시로네. 하지만 신분의 장벽이 두터운 이 곳은 아이에게 냉혹하고, 그는 채 어른이 되기도 전에 이 세상의 이면을 알아버리는데…. 어딘가 비틀어진 이 세계에서 그는 자신이 꿈꾸던 마법사가 될 수 있을까?', '무한의 마법사','https://webtoon.kakao.com/content/%EB%AC%B4%ED%95%9C%EC%9D%98-%EB%A7%88%EB%B2%95%EC%82%AC/3211',0);

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 수 END


-- 목 금
-- 목요일 연재 웹툰 - 나 혼자만 레벨업
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('현군, 장성락(REDICE STUDIO), 추공', 'https://dn-img-page.kakao.com/download/resource?kid=Cf0LJ/hynaH4y8E5/l5Qk7VWfAsyYkE8yKmRFdk&filename=th3', '나 혼자만 레벨업.jpg', 'KAKAO', 'THURSDAY', '10여 년 전, 다른 차원과 이쪽 세계를 이어 주는 통로 ''게이트''가 열리고 평범한 이들 중 각성한 자들이 생겨났다. 게이트 안의 던전에서 마물을 사냥하는 각성자. 그들을 일컬어 ''헌터''라 부른다.', '나 혼자만 레벨업','https://webtoon.kakao.com/content/%EB%82%98-%ED%98%BC%EC%9E%90%EB%A7%8C-%EB%A0%88%EB%B2%A8%EC%97%85/2320',0);

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 목요일 연재 웹툰 - 신마경천기
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('곰국, 일황', 'https://dn-img-page.kakao.com/download/resource?kid=bVT8li/hAd4aNtrvS/KwHCR3DJM3VgefYzDI7AY1&filename=th3', '신마경천기.jpg', 'KAKAO', 'THURSDAY', '금지된 마공을 익혔다는 누명을 쓰고 죽임 당한 창존문의 계승자와 제자 혁운성. 정파의 위선자들에 의한 비참하고 억울한 죽음… 운성이 죽음을 맞이하는 순간, 창존문의 신물이 빛을 발하며 새로운 인생이 주어진다.', '신마경천기','https://webtoon.kakao.com/content/%EC%8B%A0%EB%A7%88%EA%B2%BD%EC%B2%9C%EA%B8%B0/2327',0);

-- 웹툰과 장르 연결 (무협)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'MARTIAL_ARTS'), LAST_INSERT_ID());


-- 금요일 연재 웹툰 - 백작가의 망나니가 되었다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('별나래, PAN4, 유려한', 'https://dn-img-page.kakao.com/download/resource?kid=WqFpm/hAd4bSQE5E/RmvUGq8UguhbNYZUFOC5j1&filename=th3', '백작가의 망나니가 되었다.jpg', 'KAKAO', 'FRIDAY', '눈을 떠보니 소설 속이었다. 그것도 망나니로 유명한 백작가의 도련님 몸으로…. 그리고, 소설 속에서 나를 박살 냈던 막강한 주인공 최한이 곧 여기로 온다.', '백작가의 망나니가 되었다','https://webtoon.kakao.com/content/%EB%B0%B1%EC%9E%91%EA%B0%80%EC%9D%98-%EB%A7%9D%EB%82%98%EB%8B%88%EA%B0%80-%EB%90%98%EC%97%88%EB%8B%A4/2414',0);

-- 웹툰과 장르 연결 (웹툰, 드라마)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'WEBTOON'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'DRAMA'), LAST_INSERT_ID());

-- 목금 END


-- 토일

-- 토요일 연재 웹툰 - 템빨
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('이동욱,박새날', 'https://dn-img-page.kakao.com/download/resource?kid=bIFLfT/hy41I0xPNR/T9hM4wcR2U0bK7aJbkHkK1&filename=th3', '템빨.jpg', 'KAKAO', 'SATURDAY', '[신영우 (유저 네임: 그리드)] 세계 최고의 가상 현실 게임 <SATISFY>.  현실에서나, 게임 속에서나, 불운은 항상 그의 곁을 맴도는데.... 퀘스트 수행 중 우연히 발견한 게임 속 로또, ''파그마의 기서''.  20억 명이 넘는 유저 중 유일한 최강의 레전드리 직업으로 전직.  "어떻게 아직 살아있는 거지...?!"  "뭐, 특별한 건 없고..."   "템빨이죠."  지긋지긋한 불운과 악몽 같은 빚쟁이 인생에서 벗어난 최강 플레이어', '템빨','https://webtoon.kakao.com/content/%ED%85%9C%EB%B9%A8/2379',0);

-- 웹툰과 장르 연결 (웹툰, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'WEBTOON'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 토요일 연재 웹툰 - 로그인 무림
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('장철벽, 제로빅', 'https://dn-img-page.kakao.com/download/resource?kid=jnkQO/hAd4blg7Vz/3l01vJK6p8QpCKB9hJCwkk&filename=th3', '로그인 무림.jpg', 'KAKAO', 'SATURDAY', '세상의 경계인 게이트에서 몬스터 사냥을 하는 헌터들이 잘 먹고 잘 사는 시대. 주인공 진태경은 최하위 헌터라 벌이가 시원찮다. 어느 날, 낡은 VR 게임기를 줍게 되고 접속하니 무림의 세계가 펼쳐진다. 우여곡절 끝에 현실로 돌아온 태경은 무림에서 얻었던 힘이 현실에서도 그대로 남아있음을 깨닫는다. 이제 상급 헌터가 되어 돈을 버는 일만 남았는데... 게임 속 NPC 아니, 동료들이 걱정이 되어 다시 무림에 접속하게 되는데!', '로그인 무림','https://webtoon.kakao.com/content/%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EB%AC%B4%EB%A6%BC/2384',0);

-- 웹툰과 장르 연결 (웹툰, 무협)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'WEBTOON'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'MARTIAL_ARTS'), LAST_INSERT_ID());

-- 일요일 연재 웹툰 - 귀환자의 마법은 특별해야 합니다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES ('욱작가 (Ukjakga), 유소난 (Yusonan)', 'https://dn-img-page.kakao.com/download/resource?kid=9uNbq/hyRyOsWf3i/PRBfKIPzyWdl1wxk6vuYgk&filename=th3', '귀환자의 마법은 특별해야 합니다.jpg', 'KAKAO', 'SUNDAY', '다시 돌아온 이상, 사랑하는 사람들을 두 번 잃을 순 없어!" 인류가 맞이한 최악의 재앙, 그림자 미궁. 데지르 아르망은 미궁 속에서 마지막까지 살아남은 최후의 생존자 6명 중 한 명이다. 그들은 그림자 미궁의 마지막 단계에 도전하지만 결국 실패하고 세계는 멸망을 맞이하고 만다. 모든 게 끝이라 생각했던 순간, 데지르의 눈앞에 펼쳐진 것은 다름 아닌 13년 전의 세상?! 제국 최고의 마법 학원, 헤브리온에 입학했던 순간으로 돌아온 데지르', '귀환자의 마법은 특별해야 합니다','https://webtoon.kakao.com/search?keyword=%EA%B7%80%ED%99%98%EC%9E%90%EC%9D%98%20%EB%A7%88%EB%B2%95%EC%9D%80%20%ED%8A%B9%EB%B3%84%ED%95%B4%EC%95%BC%20%ED%95%A9%EB%8B%88%EB%8B%A4',0);

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 일요일 연재 웹툰 - 검술명가 막내아들
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url,review_count)
VALUES (' 이제원, 황제펭귄', 'https://dn-img-page.kakao.com/download/resource?kid=bKzthG/hzMT8bp1Ic/wN3bk5EEJamPrI1WiOuW10&filename=th3', '검술명가 막내아들.jpg', 'KAKAO', 'SUNDAY', '진 룬칸델 대륙 최고의 검술명가, 룬칸델의 막내아들 룬칸델 역사상 최악의 둔재 비참하게 쫓겨나 허무한 최후를 맞이한 그에게 다시 한 번 기회가 주어졌다. “너는 이 힘을 어떻게 사용하고 싶더냐?” “저를 위해 사용하고 싶습니다.” 전생의 기억과 압도적인 재능, 그리고 신과의 계약 최강이 될 준비는 끝났다', '검술명가 막내아들','https://webtoon.kakao.com/content/%EA%B2%80%EC%88%A0%EB%AA%85%EA%B0%80-%EB%A7%89%EB%82%B4%EC%95%84%EB%93%A4/2852',0);

-- 웹툰과 장르 연결 (웹툰, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'WEBTOON'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());



