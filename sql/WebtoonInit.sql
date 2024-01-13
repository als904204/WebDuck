-- 장르 테이블의 내용 확인
SELECT id, name FROM genre;

use webduck;
-- 왕과의 야행
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('보리', 'https://image-comic.pstatic.net/webtoon/817859/thumbnail/thumbnail_IMAG21_4fadf2ae-3467-4a9d-a6be-4b0313b963e0.jpg', '왕과의 야행.jpg', 'NAVER', 'MONDAY', '왕과의 야행 웹툰의 요약 내용', '왕과의 야행',"https://comic.naver.com/webtoon/list?titleId=817859");

-- 웹툰과 장르 연결 (로맨스)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID());

-- 백수세끼
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('치즈', 'https://image-comic.pstatic.net/webtoon/733074/thumbnail/thumbnail_IMAG21_80df3e76-47af-4007-b57c-e8f2830835e5.jpg', '백수세끼.jpg', 'NAVER', 'MONDAY', '백수세끼 웹툰의 요약 내용', '백수세끼',"https://comic.naver.com/webtoon/list?titleId=733074");

-- 웹툰과 장르 연결 (일상, 개그, 감성)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'DAILYLIFE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'GAG'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'EMOTION'), LAST_INSERT_ID());

-- 신의 탑
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('SIU', 'https://image-comic.pstatic.net/webtoon/183559/thumbnail/thumbnail_IMAG21_5f3fec31-5c95-4afe-a73f-3046288edb47.jpg', '신의 탑.jpg', 'NAVER', 'MONDAY', '신의 탑 웹툰의 요약 내용', '신의 탑',"https://comic.naver.com/webtoon/list?titleId=183559");

-- 웹툰과 장르 연결 (판타지, 액션)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'ACTION'), LAST_INSERT_ID());

-- 퀘스트지상주의
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('박태준 만화회사, 유누니 / 박태준 만화회사, 태완', 'https://image-comic.pstatic.net/webtoon/783052/thumbnail/thumbnail_IMAG21_800f4c56-26ac-419e-9ed0-baf322311dea.jpg', '퀘스트지상주의.jpg', 'NAVER', 'MONDAY', '퀘스트지상주의 웹툰의 요약 내용', '퀘스트지상주의',"https://comic.naver.com/webtoon/list?titleId=783052");

-- 웹툰과 장르 연결 (드라마, 일상)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'DRAMA'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'DAILYLIFE'), LAST_INSERT_ID());

-- 장씨세가 호위무사
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('김인호 / 조형근', 'https://image-comic.pstatic.net/webtoon/728750/thumbnail/thumbnail_IMAG21_47c21251-b213-4882-bacc-15adce1acfc8.jpg', '장씨세가 호위무사.jpg', 'NAVER', 'MONDAY', '장씨세가 호위무사 웹툰의 요약 내용', '장씨세가 호위무사',"https://comic.naver.com/webtoon/list?titleId=728750");

-- 웹툰과 장르 연결 (액션, 드라마, 무협)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ACTION'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'DRAMA'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'MARTIALARTS'), LAST_INSERT_ID());


-- 윈드브레이커
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('조용석', 'https://image-comic.pstatic.net/webtoon/602910/thumbnail/thumbnail_IMAG21_e861f2cf-6157-4d33-8e02-7b4cbf0a8baf.jpg', '윈드브레이커.jpg', 'NAVER', 'MONDAY', '윈드브레이커 웹툰의 요약 내용', '윈드브레이커',"https://comic.naver.com/webtoon/list?titleId=602910");

-- 웹툰과 장르 연결 (스포츠, 드라마)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'SPORTS'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'DRAMA'), LAST_INSERT_ID());

-- 버림받은 왕녀의 은밀한 침실
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('혜니 / 성혜림', 'https://image-comic.pstatic.net/webtoon/796867/thumbnail/thumbnail_IMAG21_77aa5064-b42b-4838-912e-2c6266c53a74.jpg', '버림받은 왕녀의 은밀한 침실.jpg', 'NAVER', 'MONDAY', '버림받은 왕녀의 은밀한 침실 웹툰의 요약 내용', '버림받은 왕녀의 은밀한 침실',"https://comic.naver.com/webtoon/list?titleId=796867");

-- 웹툰과 장르 연결 (로맨스, 청춘)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'EMOTION'), LAST_INSERT_ID());
----------------- 월요일 웹툰 END -------------------





----------------- 화요일 웹툰 START -------------------
-- 마음의소리2
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('조석', 'https://image-comic.pstatic.net/webtoon/814543/thumbnail/thumbnail_IMAG21_df84a681-b7ef-4dda-8cef-25b219d35e3e.jpg', '마음의소리2.jpg', 'NAVER', 'TUESDAY', '마음의소리2 웹툰의 요약 내용', '마음의소리2',"https://comic.naver.com/webtoon/list?titleId=814543");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'GAG')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE'));

-- 김부장
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('박태준 만화회사 / 정종택', 'https://image-comic.pstatic.net/webtoon/783053/thumbnail/thumbnail_IMAG21_d7732f14-26da-4e35-8762-660cc87b53f1.jpg', '김부장.jpg', 'NAVER', 'TUESDAY', '김부장 웹툰의 요약 내용', '김부장',"https://comic.naver.com/webtoon/list?titleId=783053");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 내가 키운 S급들
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('seri / 비완 / 근서', 'https://image-comic.pstatic.net/webtoon/784248/thumbnail/thumbnail_IMAG21_20a8aca7-23d3-4bc0-9916-a31ebc884ca8.jpg', '내가 키운 S급들.jpg', 'NAVER', 'TUESDAY', '내가 키운 S급들 웹툰의 요약 내용', '내가 키운 S급들',"https://comic.naver.com/webtoon/list?titleId=784248");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 대학원 탈출일지
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('요다', 'https://image-comic.pstatic.net/webtoon/790713/thumbnail/thumbnail_IMAG21_3919364435331003700.jpg', '대학원 탈출일지.jpg', 'NAVER', 'TUESDAY', '대학원 탈출일지 웹툰의 요약 내용', '대학원 탈출일지',"https://comic.naver.com/webtoon/list?titleId=790713");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE'));

-- 마루는 강쥐
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('모죠', 'https://image-comic.pstatic.net/webtoon/796152/thumbnail/thumbnail_IMAG21_26b9c1d8-ca2d-4fc7-87ea-a3334634236a.jpg', '마루는 강쥐.jpg', 'NAVER', 'TUESDAY', '마루는 강쥐 웹툰의 요약 내용', '마루는 강쥐',"https://comic.naver.com/webtoon/list?titleId=796152");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE'));

-- 멸망 이후의 세계
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('언데드딸기 / 언데드감자 / 싱숑', 'https://image-comic.pstatic.net/webtoon/789979/thumbnail/thumbnail_IMAG21_792cd36e-d722-4970-933b-3da7c37ee812.jpg', '멸망 이후의 세계.jpg', 'NAVER', 'TUESDAY', '멸망 이후의 세계 웹툰의 요약 내용', '멸망 이후의 세계',"https://comic.naver.com/webtoon/list?titleId=789979");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'THRILLER'));

-- 사신소년
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('류', 'https://image-comic.pstatic.net/webtoon/730656/thumbnail/thumbnail_IMAG21_fc6d8dbf-eed2-43d0-af45-2edb3cc4244e.jpg', '사신소년.jpg', 'NAVER', 'TUESDAY', '사신소년 웹툰의 요약 내용', '사신소년',"https://comic.naver.com/webtoon/list?titleId=730656");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 서울 자가에 대기업 다니는 김 부장 이야기
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('명랑 / 김병관 / 송희구', 'https://image-comic.pstatic.net/webtoon/819929/thumbnail/thumbnail_IMAG21_f1ca00e5-9b30-44d3-a1ee-7a04d539002a.jpg', '서울 자가에 대기업 다니는 김 부장 이야기.jpg', 'NAVER', 'TUESDAY', '서울 자가에 대기업 다니는 김 부장 이야기 웹툰의 요약 내용', '서울 자가에 대기업 다니는 김 부장 이야기',"https://comic.naver.com/webtoon/list?titleId=819929");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'COMEDY'));

-- 한림체육관
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('혜성 / 이석재', 'https://image-comic.pstatic.net/webtoon/743139/thumbnail/thumbnail_IMAG21_6012f944-ea1f-4328-ba22-bca09be494ee.jpg', '한림체육관.jpg', 'NAVER', 'TUESDAY', '한림체육관 웹툰의 요약 내용', '한림체육관',"https://comic.naver.com/webtoon/list?titleId=743139");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'SPORTS')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 욕망일기Deep
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('김꿀빨 / 누리마루', 'https://image-comic.pstatic.net/webtoon/818448/thumbnail/thumbnail_IMAG21_8f02d84a-1c44-412e-8a34-4c9ec09c68ac.jpg', '욕망일기Deep.jpg', 'NAVER', 'TUESDAY', '욕망일기Deep 웹툰의 요약 내용', '욕망일기Deep',"https://comic.naver.com/webtoon/list?titleId=762071");

-- 천마는 평범하게 살 수 없다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('철범 / 우푸 / 산천', 'https://image-comic.pstatic.net/webtoon/774358/thumbnail/thumbnail_IMAG21_580067f4-3434-475f-ba51-3bbc86c6ad7d.jpg', '천마는 평범하게 살 수 없다.jpg', 'NAVER', 'TUESDAY', '천마는 평범하게 살 수 없다 웹툰의 요약 내용', '천마는 평범하게 살 수 없다',"https://comic.naver.com/webtoon/list?titleId=774358");
INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'MARTIALARTS')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));




----------------- 화요일 웹툰 END -------------------










----------------- 수요일 웹툰 START ---------------------

-- 화산귀환
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('LICO / 비가', 'https://image-comic.pstatic.net/webtoon/769209/thumbnail/thumbnail_IMAG21_3511dcdd-6e33-4171-8839-598d6d266215.jpg', '화산귀환.jpg', 'NAVER', 'WEDNESDAY', '화산귀환 웹툰의 요약 내용', '화산귀환',"https://comic.naver.com/webtoon/list?titleId=769209");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'MARTIALARTS')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 전지적 독자 시점
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('UMI / 슬리피-C / 싱숑', 'https://image-comic.pstatic.net/webtoon/747269/thumbnail/thumbnail_IMAG21_aabd9952-ff45-47a2-a543-33f19a5c6708.jpg', '전지적 독자 시점.jpg', 'NAVER', 'WEDNESDAY', '전지적 독자 시점 웹툰의 요약 내용', '전지적 독자 시점',"https://comic.naver.com/webtoon/list?titleId=747269");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));

-- 무직백수 계백순
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('지발', 'https://image-comic.pstatic.net/webtoon/811721/thumbnail/thumbnail_IMAG21_9a2a959a-666b-4156-8e4f-db64dfe319c6.jpg', '무직백수 계백순.jpg', 'NAVER', 'WEDNESDAY', '무직백수 계백순 웹툰의 요약 내용', '무직백수 계백순',"https://comic.naver.com/webtoon/list?titleId=811721");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 백XX
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('병장, 박태준 만화회사 / 펀치킥', 'https://image-comic.pstatic.net/webtoon/804862/thumbnail/thumbnail_IMAG21_f1b2f920-5c10-4155-a547-0c25a7132d8f.jpeg', '백XX.jpg', 'NAVER', 'WEDNESDAY', '백XX 웹툰의 요약 내용', '백XX',"https://comic.naver.com/webtoon/list?titleId=804862");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 헬퍼 2 : 킬베로스
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('삭', 'https://image-comic.pstatic.net/webtoon/670143/thumbnail/thumbnail_IMAG21_12d5d293-d54d-4022-8e00-c97c5779c701.jpg', '헬퍼 2 : 킬베로스.jpg', 'NAVER', 'WEDNESDAY', '헬퍼 2 : 킬베로스 웹툰의 요약 내용', '헬퍼 2 : 킬베로스',"https://comic.naver.com/webtoon/list?titleId=670143");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'YOUTH')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 울어 봐, 빌어도 좋고
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('반지 / 솔체', 'https://image-comic.pstatic.net/webtoon/814826/thumbnail/thumbnail_IMAG21_1de7535f-9088-4f21-8b16-413cb1e66307.jpg', '울어 봐, 빌어도 좋고.jpg', 'NAVER', 'WEDNESDAY', '울어 봐, 빌어도 좋고 웹툰의 요약 내용', '울어 봐, 빌어도 좋고',"https://comic.naver.com/webtoon/list?titleId=814826");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'EMOTION'));

-- 일렉시드
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('손제호 / 제나', 'https://image-comic.pstatic.net/webtoon/717481/thumbnail/thumbnail_IMAG21_3545800975505057126.jpg', '일렉시드.jpg', 'NAVER', 'WEDNESDAY', '일렉시드 웹툰의 요약 내용', '일렉시드','https://webtoon.kakao.com/content/%EA%B2%80%EC%88%A0%EB%AA%85%EA%B0%80-%EB%A7%89%EB%82%B4%EC%95%84%EB%93%A4/2852');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 미래의 골동품 가게
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('구아진', 'https://image-comic.pstatic.net/webtoon/742105/thumbnail/thumbnail_IMAG21_a3860cc1-f927-4031-92fd-8d89b9801127.jpg', '미래의 골동품 가게.jpg', 'NAVER', 'WEDNESDAY', '미래의 골동품 가게 웹툰의 요약 내용', '미래의 골동품 가게',"https://comic.naver.com/webtoon/list?titleId=742105");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 격기3반
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('이학', 'https://image-comic.pstatic.net/webtoon/701535/thumbnail/thumbnail_IMAG21_2abe5b1a-9104-417f-9995-0db15c6db7be.jpg', '격기3반.jpg', 'NAVER', 'WEDNESDAY', '격기3반 웹툰의 요약 내용', '격기3반',"https://comic.naver.com/webtoon/list?titleId=701535");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'SPORTS'));

-- 어린이집 다니는 구나
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('구나', 'https://image-comic.pstatic.net/webtoon/817945/thumbnail/thumbnail_IMAG21_120ad315-8518-4430-bb30-739654b49635.jpg', '어린이집 다니는 구나.jpg', 'NAVER', 'WEDNESDAY', '어린이집 다니는 구나 웹툰의 요약 내용', '어린이집 다니는 구나',"https://comic.naver.com/webtoon/list?titleId=817945");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'COMEDY'));

-- 칼에 취한 밤을 걷다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('JP / 송민 / 유진성', 'https://image-comic.pstatic.net/webtoon/795542/thumbnail/thumbnail_IMAG21_3703756833080816441.jpg', '칼에 취한 밤을 걷다.jpg', 'NAVER', 'WEDNESDAY', '칼에 취한 밤을 걷다 웹툰의 요약 내용', '칼에 취한 밤을 걷다',"https://comic.naver.com/webtoon/list?titleId=795542");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

----------------- 수요일 웹툰 END ---------------------




----------------- 목요일 웹툰 START ---------------------
-- 선천적 얼간이들
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('가스파드', 'https://image-comic.pstatic.net/webtoon/478261/thumbnail/thumbnail_IMAG21_7fbd8610-0a97-40e3-9c63-101ea07fc4b4.jpg', '선천적 얼간이들.jpg', 'NAVER', 'THURSDAY', '선천적 얼간이들 웹툰의 요약 내용', '선천적 얼간이들',"https://comic.naver.com/webtoon/list?titleId=478261");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DAILY_LIFE'));

-- 앞집나리
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('민송아', 'https://image-comic.pstatic.net/webtoon/802293/thumbnail/thumbnail_IMAG21_a3e4736d-dfd6-4e76-aec6-b145f4123845.jpg', '앞집나리.jpg', 'NAVER', 'THURSDAY', '앞집나리 웹툰의 요약 내용', '앞집나리',"https://comic.naver.com/webtoon/list?titleId=802293");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'YOUTH')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 나노마신
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('현절무, 금강불괴, 한중월야', 'https://image-comic.pstatic.net/webtoon/747271/thumbnail/thumbnail_IMAG21_75c6a3cc-c4d1-4b51-a945-029aff772a63.jpg', '나노마신.jpg', 'NAVER', 'THURSDAY', '나노마신 웹툰의 요약 내용', '나노마신',"https://comic.naver.com/search?keyword=%EB%82%98%EB%85%B8");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 촉법소년
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('박태준 만화회사, 남자의 이야기 / 정종택', 'https://image-comic.pstatic.net/webtoon/808198/thumbnail/thumbnail_IMAG21_2aabb538-5b1a-4d74-81ed-b41bb8a155ff.jpg', '촉법소년.jpg', 'NAVER', 'THURSDAY', '촉법소년 웹툰의 요약 내용', '촉법소년',"https://comic.naver.com/webtoon/list?titleId=808198");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 재벌집 막내아들
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('JP / 김병관 / 산경(山景)', 'https://image-comic.pstatic.net/webtoon/800770/thumbnail/thumbnail_IMAG21_d2e1e7ee-fc83-4030-a1e7-200378bc088f.jpg', '재벌집 막내아들.jpg', 'NAVER', 'THURSDAY', '재벌집 막내아들 웹툰의 요약 내용', '재벌집 막내아들',"https://comic.naver.com/webtoon/list?titleId=800770");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 맛집
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('밍규 / 드로잉창고', 'https://image-comic.pstatic.net/webtoon/813843/thumbnail/thumbnail_IMAG21_7bbc3b88-f67b-4eae-ad76-2531151984a7.jpg', '맛집.jpg', 'NAVER', 'THURSDAY', '맛집 웹툰의 요약 내용', '맛집',"https://comic.naver.com/webtoon/list?titleId=813843");

-- 무사만리행
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('운, 배민기', 'https://image-comic.pstatic.net/webtoon/746857/thumbnail/thumbnail_IMAG21_fb7d7fcd-19f1-49eb-9cc8-aae9622cdd04.jpg', '무사만리행.jpg', 'NAVER', 'THURSDAY', '무사만리행 웹툰의 요약 내용', '무사만리행',"https://comic.naver.com/webtoon/list?titleId=746857");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'MARTIALARTS'));
----------------- 목요일 웹툰 END ---------------------


----------------- 금요일 웹툰 START ---------------------
-- 외모지상주의
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('박태준', 'https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG21_01672165-03c8-44b1-ba0e-ef82c9cfcd10.jpg', '외모지상주의.jpg', 'NAVER', 'FRIDAY', '외모지상주의 웹툰의 요약 내용', '외모지상주의',"https://comic.naver.com/webtoon/list?titleId=641253");

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 광마회귀
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('JP / 이히 / 유진성', 'https://image-comic.pstatic.net/webtoon/776601/thumbnail/thumbnail_IMAG21_7365135131254864183.jpg', '광마회귀.jpg', 'NAVER', 'FRIDAY', '광마회귀 웹툰의 요약 내용', '광마회귀','https://comic.naver.com/webtoon/list?titleId=776601');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 역대급 영지 설계사
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('이현민, 김현수, 문백경', 'https://image-comic.pstatic.net/webtoon/777767/thumbnail/thumbnail_IMAG21_cc85f891-272b-450a-b642-cffe1568ab71.jpg', '역대급 영지 설계사.jpg', 'NAVER', 'FRIDAY', '역대급 영지 설계사 웹툰의 요약 내용', '역대급 영지 설계사','https://comic.naver.com/webtoon/list?titleId=777767');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));

-- 나 혼자 만렙 뉴비
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('WAN.Z, 스윙뱃, 메슬로우', 'https://image-comic.pstatic.net/webtoon/773797/thumbnail/thumbnail_IMAG21_4dda13fe-417f-45b9-9696-880f2487d41d.jpg', '나 혼자 만렙 뉴비.jpg', 'NAVER', 'FRIDAY', '나 혼자 만렙 뉴비 웹툰의 요약 내용', '나 혼자 만렙 뉴비','https://comic.naver.com/webtoon/list?titleId=773797');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 재혼 황후
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('히어리, 숨풀, 알파타르트', 'https://image-comic.pstatic.net/webtoon/735661/thumbnail/thumbnail_IMAG21_4b3c44a0-f286-4878-bac3-84c3ec9dc0a1.jpg', '재혼 황후.jpg', 'NAVER', 'FRIDAY', '재혼 황후 웹툰의 요약 내용', '재혼 황후','https://comic.naver.com/webtoon/list?titleId=735661');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ROMANCE')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));



-- 상남자
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('하늘소 / 도가도 / 김태궁', 'https://image-comic.pstatic.net/webtoon/751168/thumbnail/thumbnail_IMAG21_529c6125-cf14-435f-94ed-db1a2d499d84.jpg', '상남자.jpg', 'NAVER', 'FRIDAY', '상남자 웹툰의 요약 내용', '상남자','https://comic.naver.com/webtoon/list?titleId=751168');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 언니, 이번 생엔 내가 왕비야
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('Omin / 테미스 / 레팔진프', 'https://image-comic.pstatic.net/webtoon/798917/thumbnail/thumbnail_IMAG21_35c1a390-7598-418e-9902-dc9a84f22d8b.jpg', '언니, 이번 생엔 내가 왕비야.jpg', 'NAVER', 'FRIDAY', '언니, 이번 생엔 내가 왕비야 웹툰의 요약 내용', '언니, 이번 생엔 내가 왕비야','https://comic.naver.com/webtoon/list?titleId=798917');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

----------------- 금요일 웹툰 END ---------------------



----------------- 토요일 웹툰 START ---------------------
-- 초인의 시대
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('섭이', 'https://image-comic.pstatic.net/webtoon/730694/thumbnail/thumbnail_IMAG21_e6fc219d-e5ea-4d93-b7d6-45b595c2a3cb.jpeg', '초인의 시대.jpg', 'NAVER', 'SATURDAY', '초인의 시대 웹툰의 요약 내용', '초인의 시대','https://comic.naver.com/webtoon/list?titleId=730694');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 마도전생기
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('포스스튜디오 / codezero', 'https://image-comic.pstatic.net/webtoon/807777/thumbnail/thumbnail_IMAG21_e47489ea-bf97-4726-a8e7-6403092a303a.jpg', '마도전생기.jpg', 'NAVER', 'SATURDAY', '마도전생기 웹툰의 요약 내용', '마도전생기','https://comic.naver.com/webtoon/list?titleId=807777');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'MARTIALARTS'));


-- 귀환했는데 입대 전날이다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('해일 / 오코믹 / 황금비둘기', 'https://image-comic.pstatic.net/webtoon/807393/thumbnail/thumbnail_IMAG21_aff8a206-014c-471f-9e91-c569940ad8e5.jpg', '귀환했는데 입대 전날이다.jpg', 'NAVER', 'SATURDAY', '귀환했는데 입대 전날이다 웹툰의 요약 내용', '귀환했는데 입대 전날이다','https://comic.naver.com/webtoon/list?titleId=807393');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));

----------------- 토요일 웹툰 END ---------------------




----------------- 일요일 웹툰 START ---------------------
-- 싸움독학
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('박태준 만화회사 / 김정현 스튜디오', 'https://image-comic.pstatic.net/webtoon/736277/thumbnail/thumbnail_IMAG21_bbbe3f76-021e-4dbc-830a-4358c1abec0c.jpg', '싸움독학.jpg', 'NAVER', 'SUNDAY', '싸움독학 웹툰의 요약 내용', '싸움독학','https://comic.naver.com/webtoon/list?titleId=736277');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 입학용병
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('YC / 락현', 'https://image-comic.pstatic.net/webtoon/758150/thumbnail/thumbnail_IMAG21_4135492154714961716.jpg', '입학용병.jpg', 'NAVER', 'SUNDAY', '입학용병 웹툰의 요약 내용', '입학용병','https://comic.naver.com/webtoon/list?titleId=758150');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'STORY')),
       (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 투신전생기
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('청담', 'https://image-comic.pstatic.net/webtoon/774044/thumbnail/thumbnail_IMAG21_81504afb-1a05-41b0-9650-0c9aa1d741d9.jpg', '투신전생기.jpg', 'NAVER', 'SUNDAY', '투신전생기 웹툰의 요약 내용', '투신전생기','https://comic.naver.com/webtoon/list?titleId=774044');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));

-- 시월드가 내게 집착한다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('승우 / 한윤설', 'https://image-comic.pstatic.net/webtoon/785251/thumbnail/thumbnail_IMAG21_c71f43a5-e252-4466-93af-ff7a606b271f.jpg', '시월드가 내게 집착한다.jpg', 'NAVER', 'SUNDAY', '시월드가 내게 집착한다 웹툰의 요약 내용', '시월드가 내게 집착한다','https://comic.naver.com/webtoon/list?titleId=785251');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ROMANCE'));

-- 별이삼샵
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('혀노', 'https://image-comic.pstatic.net/webtoon/737628/thumbnail/thumbnail_IMAG21_3774405050062168629.jpg', '별이삼샵.jpg', 'NAVER', 'SUNDAY', '별이삼샵 웹툰의 요약 내용', '별이삼샵','https://comic.naver.com/webtoon/list?titleId=737628');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));

-- 천화서고 대공자
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('128 / MIDNIGHT STUDIO / 김현영', 'https://image-comic.pstatic.net/webtoon/805193/thumbnail/thumbnail_IMAG21_47d9dfe3-8e58-43ba-b9c1-ad18673068cb.jpg', '천화서고 대공자.jpg', 'NAVER', 'SUNDAY', '천화서고 대공자 웹툰의 요약 내용', '천화서고 대공자','https://comic.naver.com/webtoon/list?titleId=805193');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'ACTION'));

-- 가비지타임
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('2사장', 'https://image-comic.pstatic.net/webtoon/703844/thumbnail/thumbnail_IMAG21_5ddcb40e-1f6a-40f3-b2c4-6cd9a7eee843.jpg', '가비지타임.jpg', 'NAVER', 'SUNDAY', '가비지타임 웹툰의 요약 내용', '가비지타임','https://comic.naver.com/webtoon/list?titleId=703844');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'DRAMA'));

-- 수희0(tngmlek0)
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('생일기분', 'https://image-comic.pstatic.net/webtoon/774831/thumbnail/thumbnail_IMAG21_b4644a73-ecfb-4532-a96c-575b02accfd0.jpg', '수희0(tngmlek0).jpg', 'NAVER', 'SUNDAY', '수희0(tngmlek0) 웹툰의 요약 내용', '수희0(tngmlek0)','https://comic.naver.com/webtoon/list?titleId=774831');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'THRILLER'));

-- 나 혼자 특성빨로 무한 성장
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('샤이아탄 / 2사랑 / 선운(鮮雲)', 'https://image-comic.pstatic.net/webtoon/803909/thumbnail/thumbnail_IMAG21_764d377c-fe45-40f9-8775-0f6ff92ad421.jpg', '나 혼자 특성빨로 무한 성장.jpg', 'NAVER', 'SUNDAY', '나 혼자 특성빨로 무한 성장 웹툰의 요약 내용', '나 혼자 특성빨로 무한 성장','https://comic.naver.com/webtoon/list?titleId=803909');

INSERT INTO webtoon_genre (webtoon_id, genre_id)
VALUES (LAST_INSERT_ID(), (SELECT id FROM genre WHERE name = 'FANTASY'));

----------------- 일요일 웹툰 END ---------------------


----------------------카카오----------------------

----------------- 월요일 웹툰 START ---------------------
-- 이번 생, 끝까지 살아남겠습니다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('아름, 민은경, 니나노솔', 'https://dn-img-page.kakao.com/download/resource?kid=dfbTHX/hAd4tGpidC/9Z14rufbv4OO0dhxlfQZCK&filename=th3', '이번 생, 끝까지 살아남겠습니다.jpg', 'KAKAO', 'MONDAY', '이번 생, 끝까지 살아남겠습니다 웹툰의 요약 내용', '이번 생, 끝까지 살아남겠습니다','https://webtoon.kakao.com/content/%EC%9D%B4%EB%B2%88-%EC%83%9D-%EB%81%9D%EA%B9%8C%EC%A7%80-%EC%82%B4%EC%95%84%EB%82%A8%EA%B2%A0%EC%8A%B5%EB%8B%88%EB%8B%A4/3903');

-- 웹툰과 장르 연결 (로맨스, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 이번 생은 가주가 되겠습니다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('앤트스튜디오, 몬, 김로아', 'https://dn-img-page.kakao.com/download/resource?kid=RbyC9/hAd4sHh3Rl/Hkc73O0AxXz4CotZnvifp0&filename=th3', '이번 생은 가주가 되겠습니다.jpg', 'KAKAO', 'MONDAY', '이번 생은 가주가 되겠습니다 웹툰의 요약 내용', '이번 생은 가주가 되겠습니다','https://webtoon.kakao.com/content/%EC%9D%B4%EB%B2%88-%EC%83%9D%EC%9D%80-%EA%B0%80%EC%A3%BC%EA%B0%80-%EB%90%98%EA%B2%A0%EC%8A%B5%EB%8B%88%EB%8B%A4/2473');

-- 웹툰과 장르 연결 (로맨스, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 묵향 다크레이디
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('이재헌, 구깃, 전동조', 'https://dn-img-page.kakao.com/download/resource?kid=bRsIke/hyoXBaoZrt/TWbrWl7ATyqkfxIiDdOKn0&filename=th3', '묵향 다크레이디.jpg', 'KAKAO', 'MONDAY', '묵향 다크레이디 웹툰의 요약 내용', '묵향 다크레이디','https://webtoon.kakao.com/content/%EB%AC%B5%ED%96%A5-%EB%8B%A4%ED%81%AC%EB%A0%88%EC%9D%B4%EB%94%94/2341');

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 사랑받는 막내는 처음이라
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('나정, 와와치, 미래나비', 'https://dn-img-page.kakao.com/download/resource?kid=wipRk/hAdNZMhMcu/gKXTSHIZmX77FjLMRIysmk&filename=th3', '사랑받는 막내는 처음이라.jpg', 'KAKAO', 'MONDAY', '사랑받는 막내는 처음이라 웹툰의 요약 내용', '사랑받는 막내는 처음이라','https://webtoon.kakao.com/content/%EC%82%AC%EB%9E%91%EB%B0%9B%EB%8A%94-%EB%A7%89%EB%82%B4%EB%8A%94-%EC%B2%98%EC%9D%8C%EC%9D%B4%EB%9D%BC/3835');

-- 웹툰과 장르 연결 (로맨스, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 철혈검가 사냥개의 회귀
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('이산책(REDICE STUDIO), 설아랑, 레고밟았어', 'https://dn-img-page.kakao.com/download/resource?kid=cWu2nJ/hzVqKMVQuF/hSeVf9wqXVZsal7kjHn9tk&filename=th3', '철혈검가 사냥개의 회귀.jpg', 'KAKAO', 'MONDAY', '철혈검가 사냥개의 회귀 웹툰의 요약 내용', '철혈검가 사냥개의 회귀','https://webtoon.kakao.com/content/%EC%B2%A0%ED%98%88%EA%B2%80%EA%B0%80-%EC%82%AC%EB%83%A5%EA%B0%9C%EC%9D%98-%ED%9A%8C%EA%B7%80/3455');

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

----------------- 일요일 웹툰 END ---------------------


----------------- 화요일 웹툰 START---------------------
-- 다정한 그대를 지키는 방법
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('하운드, 김지의, 마약젤리', 'https://dn-img-page.kakao.com/download/resource?kid=etke9/hAd4UDU7Ln/ZWFOMebkRm8woCaZoMtVA0&filename=th3', '다정한 그대를 지키는 방법.jpg', 'KAKAO', 'TUESDAY', '다정한 그대를 지키는 방법 웹툰의 요약 내용', '다정한 그대를 지키는 방법','https://webtoon.kakao.com/content/%EB%8B%A4%EC%A0%95%ED%95%9C-%EA%B7%B8%EB%8C%80%EB%A5%BC-%EC%A7%80%ED%82%A4%EB%8A%94-%EB%B0%A9%EB%B2%95/2485');

-- 웹툰과 장르 연결 (로맨스, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 2레벨로 회귀한 무신
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('디앤씨웹툰, 이윤구, 염비', 'https://dn-img-page.kakao.com/download/resource?kid=BEkmh/hzN2ocRhjr/NRzjwVW0zTNYyUPcwPwk80&filename=th3', '2레벨로 회귀한 무신.jpg', 'KAKAO', 'TUESDAY', '2레벨로 회귀한 무신 웹툰의 요약 내용', '2레벨로 회귀한 무신','https://webtoon.kakao.com/content/2%EB%A0%88%EB%B2%A8%EB%A1%9C-%ED%9A%8C%EA%B7%80%ED%95%9C-%EB%AC%B4%EC%8B%A0/3634');

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());


----------------- 화요ㅣ일 웹툰 END ---------------------


----------------- 수일 웹툰 START ---------------------
-- 황제의 아이를 숨기는 방법
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('쌀, 이연선', 'https://dn-img-page.kakao.com/download/resource?kid=bk77pD/hzXarSM7qN/kl75PjbZNx3LYhWZMMuDDK&filename=th3', '황제의 아이를 숨기는 방법.jpg', 'KAKAO', 'WEDNESDAY', '황제의 아이를 숨기는 방법 웹툰의 요약 내용', '황제의 아이를 숨기는 방법','https://webtoon.kakao.com/content/%ED%99%A9%EC%A0%9C%EC%9D%98-%EC%95%84%EC%9D%B4%EB%A5%BC-%EC%88%A8%EA%B8%B0%EB%8A%94-%EB%B0%A9%EB%B2%95/2881');

-- 웹툰과 장르 연결 (로맨스, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 북검전기
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('해민, 우각', 'https://dn-img-page.kakao.com/download/resource?kid=bKwtap/hzVqIa828J/5yBrt9O6oDZnyN0iTc622K&filename=th3', '북검전기.jpg', 'KAKAO', 'WEDNESDAY', '북검전기 웹툰의 요약 내용', '북검전기','https://webtoon.kakao.com/content/%EB%B6%81%EA%B2%80%EC%A0%84%EA%B8%B0/2362');

-- 웹툰과 장르 연결 (무협)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'MARTIAL_ARTS'), LAST_INSERT_ID());

-- 무한의 마법사
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('kiraz(REDICE STUDIO), 테미스, 김치우', 'https://dn-img-page.kakao.com/download/resource?kid=bbXpgU/hyoXzXU0Zn/y5Ae8SzxX1EorWXZH6Ez90&filename=th3', '무한의 마법사.jpg', 'KAKAO', 'WEDNESDAY', '무한의 마법사 웹툰의 요약 내용', '무한의 마법사','https://webtoon.kakao.com/content/%EB%AC%B4%ED%95%9C%EC%9D%98-%EB%A7%88%EB%B2%95%EC%82%AC/3211');

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-----------------  수 웹툰 END ---------------------


-----------------  목 금 웹툰 START ---------------------
-- 목요일 연재 웹툰 - 나 혼자만 레벨업
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('현군, 장성락(REDICE STUDIO), 추공', 'https://dn-img-page.kakao.com/download/resource?kid=Cf0LJ/hynaH4y8E5/l5Qk7VWfAsyYkE8yKmRFdk&filename=th3', '나 혼자만 레벨업.jpg', 'KAKAO', 'THURSDAY', '나 혼자만 레벨업 웹툰의 요약 내용', '나 혼자만 레벨업','https://webtoon.kakao.com/content/%EB%82%98-%ED%98%BC%EC%9E%90%EB%A7%8C-%EB%A0%88%EB%B2%A8%EC%97%85/2320');

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 목요일 연재 웹툰 - 신마경천기
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('곰국, 일황', 'https://dn-img-page.kakao.com/download/resource?kid=bVT8li/hAd4aNtrvS/KwHCR3DJM3VgefYzDI7AY1&filename=th3', '신마경천기.jpg', 'KAKAO', 'THURSDAY', '신마경천기 웹툰의 요약 내용', '신마경천기','https://webtoon.kakao.com/content/%EC%8B%A0%EB%A7%88%EA%B2%BD%EC%B2%9C%EA%B8%B0/2327');

-- 웹툰과 장르 연결 (무협)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'MARTIAL_ARTS'), LAST_INSERT_ID());

-- 목요일 연재 웹툰 - 악당의 아빠를 꼬셔라
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('비아, 달슬', 'https://dn-img-page.kakao.com/download/resource?kid=h8sy3/hAdNSl5dJ9/Ri83GJb3sk4FPjXk0lva10&filename=th3', '악당의 아빠를 꼬셔라.jpg', 'KAKAO', 'THURSDAY', '악당의 아빠를 꼬셔라 웹툰의 요약 내용', '악당의 아빠를 꼬셔라','https://webtoon.kakao.com/content/%EC%95%85%EB%8B%B9%EC%9D%98-%EC%95%84%EB%B9%A0%EB%A5%BC-%EA%BC%AC%EC%85%94%EB%9D%BC/2378');

-- 웹툰과 장르 연결 (로맨스, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'ROMANCE'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 금요일 연재 웹툰 - 백작가의 망나니가 되었다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('별나래, PAN4, 유려한', 'https://dn-img-page.kakao.com/download/resource?kid=WqFpm/hAd4bSQE5E/RmvUGq8UguhbNYZUFOC5j1&filename=th3', '백작가의 망나니가 되었다.jpg', 'KAKAO', 'FRIDAY', '백작가의 망나니가 되었다 웹툰의 요약 내용', '백작가의 망나니가 되었다','https://webtoon.kakao.com/content/%EB%B0%B1%EC%9E%91%EA%B0%80%EC%9D%98-%EB%A7%9D%EB%82%98%EB%8B%88%EA%B0%80-%EB%90%98%EC%97%88%EB%8B%A4/2414');

-- 웹툰과 장르 연결 (웹툰, 드라마)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'WEBTOON'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'DRAMA'), LAST_INSERT_ID());

-----------------  목 금 웹툰 END ---------------------














----------------- 토일 웹툰 START ---------------------

-- 토요일 연재 웹툰 - 템빨
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('이동욱,박새날', 'https://dn-img-page.kakao.com/download/resource?kid=bIFLfT/hy41I0xPNR/T9hM4wcR2U0bK7aJbkHkK1&filename=th3', '템빨.jpg', 'KAKAO', 'SATURDAY', '템빨 웹툰의 요약 내용', '템빨','https://webtoon.kakao.com/content/%ED%85%9C%EB%B9%A8/2379');

-- 웹툰과 장르 연결 (웹툰, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'WEBTOON'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 토요일 연재 웹툰 - 로그인 무림
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('장철벽, 제로빅', 'https://dn-img-page.kakao.com/download/resource?kid=jnkQO/hAd4blg7Vz/3l01vJK6p8QpCKB9hJCwkk&filename=th3', '로그인 무림.jpg', 'KAKAO', 'SATURDAY', '로그인 무림 웹툰의 요약 내용', '로그인 무림','https://webtoon.kakao.com/content/%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EB%AC%B4%EB%A6%BC/2384');

-- 웹툰과 장르 연결 (웹툰, 무협)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'WEBTOON'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'MARTIAL_ARTS'), LAST_INSERT_ID());

-- 일요일 연재 웹툰 - 귀환자의 마법은 특별해야 합니다
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES ('욱작가 (Ukjakga), 유소난 (Yusonan)', 'https://dn-img-page.kakao.com/download/resource?kid=9uNbq/hyRyOsWf3i/PRBfKIPzyWdl1wxk6vuYgk&filename=th3', '귀환자의 마법은 특별해야 합니다.jpg', 'KAKAO', 'SUNDAY', '귀환자의 마법은 특별해야 합니다 웹툰의 요약 내용', '귀환자의 마법은 특별해야 합니다','https://webtoon.kakao.com/search?keyword=%EA%B7%80%ED%99%98%EC%9E%90%EC%9D%98%20%EB%A7%88%EB%B2%95%EC%9D%80%20%ED%8A%B9%EB%B3%84%ED%95%B4%EC%95%BC%20%ED%95%A9%EB%8B%88%EB%8B%A4');

-- 웹툰과 장르 연결 (판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());

-- 일요일 연재 웹툰 - 검술명가 막내아들
INSERT INTO webtoon (author, image_path, original_image_name, platform, publish_day, summary, title,webtoon_url)
VALUES (' 이제원, 황제펭귄', 'https://dn-img-page.kakao.com/download/resource?kid=bKzthG/hzMT8bp1Ic/wN3bk5EEJamPrI1WiOuW10&filename=th3', '검술명가 막내아들.jpg', 'KAKAO', 'SUNDAY', '검술명가 막내아들 웹툰의 요약 내용', '검술명가 막내아들','https://webtoon.kakao.com/content/%EA%B2%80%EC%88%A0%EB%AA%85%EA%B0%80-%EB%A7%89%EB%82%B4%EC%95%84%EB%93%A4/2852');

-- 웹툰과 장르 연결 (웹툰, 판타지)
INSERT INTO webtoon_genre (genre_id, webtoon_id)
VALUES ((SELECT id FROM genre WHERE name = 'WEBTOON'), LAST_INSERT_ID()),
       ((SELECT id FROM genre WHERE name = 'FANTASY'), LAST_INSERT_ID());
----------------- 토일 웹툰 END ---------------------



