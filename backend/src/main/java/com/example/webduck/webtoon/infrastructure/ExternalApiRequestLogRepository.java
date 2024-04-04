package com.example.webduck.webtoon.infrastructure;

import com.example.webduck.webtoon.infrastructure.ExternalApiRequestLog;
import com.example.webduck.webtoon.infrastructure.Platform;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalApiRequestLogRepository extends JpaRepository<ExternalApiRequestLog,Long> {

    Optional<ExternalApiRequestLog> findByPlatform(Platform platform);
}
