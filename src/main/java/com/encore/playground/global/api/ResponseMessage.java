package com.encore.playground.global.api;

public class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String INCORRECT_PASSWORD = "잘못된 패스워드";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String REGISTER_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String UNREGISTER_USER = "회원 탈퇴 성공";
    public static final String QNA_WRITE_SUCCESS = "질문 등록 성공";
    public static final String QNA_VIEW_SUCCESS = "질문 가져오기 성공";
    public static final String QNA_MODIFY = "질문 수정 성공";
    public static final String QNA_MODIFY_FAILED = "질문 수정 실패";
    public static final String QNA_MODIFY_ACCESS = "질문 수정 접근 성공";
    public static final String QNA_DELETE = "질문 삭제 성공";
    public static final String QNA_DELETE_FAILED = "질문 삭제 실패";
    public static final String FEED_DELETE = "피드 삭제 성공";
    public static final String FEED_DELETE_FAILED = "피드 삭제 실패";
    public static final String FEED_MODIFY = "피드 수정 성공";
    public static final String FEED_MODIFY_FAILED = "피드 수정 실패";
    public static final String COMMENT_DELETE = "댓글 삭제 성공";
    public static final String COMMENT_DELETE_FAILED = "댓글 삭제 실패";
    public static final String COMMENT_MODIFY = "댓글 수정 성공";
    public static final String COMMENT_MODIFY_FAILED = "댓글 수정 실패";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
}
