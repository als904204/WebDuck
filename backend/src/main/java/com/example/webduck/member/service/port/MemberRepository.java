package com.example.webduck.member.service.port;

import com.example.webduck.member.domain.Member;
import com.example.webduck.member.infrastructure.SocialType;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findByEmailAndSocialType(String email, SocialType socialType);
    Optional<Member> findById(Long id);
    Member getById(Long id);
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);

}
