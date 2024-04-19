package com.example.webduck.webtoon.infrastructure;

import com.example.webduck.global.common.BaseTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "external_api_request_log")
public class ExternalApiRequestLog extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform",nullable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(name = "last_requested_page", nullable = false)
    private int lastRequestedPage;



    public ExternalApiRequestLog(Platform platform) {
        this.platform = platform;
        this.lastRequestedPage = 500; // 500 페이지까지 순정웹툰만있음
    }


    public void setLastRequestedPage(int pageNo) {
        this.lastRequestedPage = pageNo;
    }

    // 마지막으로 요청한 +1 페이지
    public int getStartPage() {
        return this.lastRequestedPage + 1;
    }

    // 마지막으로 요청한 페이지부터 20페이지
    public int getLastPage() {
        return getStartPage() + 20;
    }
}
