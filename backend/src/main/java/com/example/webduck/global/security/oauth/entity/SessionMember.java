package com.example.webduck.global.security.oauth.entity;

import com.example.webduck.member.entity.Member;

import java.io.Serializable;

/**
 * TODO : 현재 서비스는 애플리케이션을 재실행하면 로그인이 풀린다
 * WHY?  세션이 내장 톰캣의 메모리에 저장되기 때문
 *
 * 문제점 1 : 메모리에 저장되다 보니 내장 톰캣처럼 애플리케이션 실행 시 실행되는 구조에선 항상 초기화(배포할때마다 톰켓 재시작)
 * 문제점 2 : 2대 이상이 서버에서 서비스하고 있다면 톰캣마다 세션 동기화 설정
 * 해결법 : JWT stateless 로 구현, Redis를 사용한 세션 관리(따로 세션서버를 구성 빠르지만 비쌈),Database 기반 세션 관리(느리지만 쌈)
 */
public class SessionMember implements Serializable {


    private static final long serialVersionUID = -3094014404731310385L;
    private final Long id;
    private String username;

    public SessionMember(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
