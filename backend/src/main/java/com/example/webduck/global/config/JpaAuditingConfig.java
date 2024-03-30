package com.example.webduck.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * EnableJpaAuditing 설정 클래스
 * Main -> Config 로 따로 구성한 이유는 Controller 테스트코드(@WebMvcTest) 작성할 때
 * @WebMvcTest가 전체 스프링 컨텍스트를 로드하지 않기 때문에 JPA 메타모델이 초기화되지 않아 오류 발생
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {}
