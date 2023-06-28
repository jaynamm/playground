insert into feed (comment_count, comment_total_count, content, created_date, like_count, member_id, modified_date, view_count) values (0, 0, "나 오늘 버거킹 불고기 와퍼 먹을 예정", '2023-05-18T19:27:41.487261', 0, "유수빈", '2023-05-18T19:27:41.487261', 0);
insert into feed (comment_count, comment_total_count, content, created_date, like_count, member_id, modified_date, view_count) values (0, 0, "아 배고파 그래서 오점뭐?", '2023-05-18T19:27:41.487261', 0, "남민우", '2023-05-18T19:27:41.487261', 0);
insert into feed (comment_count, comment_total_count, content, created_date, like_count, member_id, modified_date, view_count) values (0, 0, "오늘 맥주 먹을 사람? 고량주 ㄱ?", '2023-05-18T19:27:41.487261', 0, "김용호", '2023-05-18T19:27:41.487261', 0);
insert into feed (comment_count, comment_total_count, content, created_date, like_count, member_id, modified_date, view_count) values (0, 0, "이클립스나 멘토스 혹시 더 있나요?", '2023-05-18T19:27:41.487261', 0, "권광일", '2023-05-18T19:27:41.487261', 0);

insert into notice(title, content, created_date, modified_date, member_id) values ("[안내] 캠퍼스 이전 관련 공지입니다.", "현재 운영 중인 독산 캠퍼스를 수원으로 이전하게 되었습니다.", 20230512120122, 20230512120122,"매니저");
insert into notice(title, content, created_date, modified_date, member_id) values ("[긴급] 교재 수령 안내", "21기 수강생 여러분들은 빠른 시일 내에 교재를 받지 않으면 큰일이 납니다.", 20230516200134, 20230516200134, "매니저");
insert into notice(title, content, created_date, modified_date, member_id) values ("[안내] 캠퍼스 휴무일 공지", "독산 캠퍼스는 매주 월요일 쉬기로 결정했습니다.", 20230518091726, 20230518091726, "매니저");
insert into notice(title, content, created_date, modified_date, member_id) values ("[자격증] 정보처리기사 접수 일정 ", "접수일정: 2023.06.17.(월) 10:00 ~ 2023.06.20.(목) 18:00", 20230518151726, 20230518151726, "매니저");
insert into notice(title, content, created_date, modified_date, member_id) values ("[신청]  LET'S PLAY 현직자 Session 사전조사 ", "의견을 반영하고자 사전 설문조사를 진행하고자 하니 사전조사 설문에 참여해주십시오.", 20230518224202, 20230518224202, "매니저");

insert into question(title, content, member_id, created_date) values ("공가 사용에 대해 질문이 있습니다.", "한번에 공가를 몇개까지 사용할 수 있나요? ", "남민우", 20230518091726);
insert into question(title, content, member_id, created_date) values ("독산 캠퍼스 운영시간이 궁금합니다.", "몇시까지 운영하나요? ", "김희주", 20230519221726);
insert into question(title, content, member_id, created_date) values ("도서 대출 관련 질문입니다.", "책 대여기간은 며칠인가요?", "유수빈", 20230519231726);

INSERT INTO playground.member (id, created_date, curriculum, email, name, nickname, password, modified_date, role, userid) VALUES (1, '2023-05-17 16:54:04.593788', 'qwer', 'qwer@qwer', 'qwer', 'qwer', '$2a$10$9KexgHrV.lFGxIonm8Q7yuwvEfwMeoi2YSo.uYznryT1Kypoa/tMG', '2023-05-17 16:54:04.593788', 'USER', 'qwer');

INSERT INTO playground.comment (id, content, created_date, like_count, member_id, modified_date, feed_id) VALUES (2, '댓글 내용', '2023-05-17 19:37:35.890895', 0, '작성자', '2023-05-17 19:37:35.890895', 1);
INSERT INTO playground.comment (id, content, created_date, like_count, member_id, modified_date, feed_id) VALUES (3, '다', '2023-05-17 19:37:36.881436', 0, '존', '2023-05-17 19:37:36.881436', 1);
INSERT INTO playground.comment (id, content, created_date, like_count, member_id, modified_date, feed_id) VALUES (4, '난 피자가 싫어', '2023-05-17 19:37:37.770748', 0, '도우', '2023-05-17 19:37:37.770748', 1);
INSERT INTO playground.comment (id, content, created_date, like_count, member_id, modified_date, feed_id) VALUES (5, '난 댓글이야', '2023-05-17 19:37:38.571587', 0, '댓글', '2023-05-17 19:37:38.571587', 1);