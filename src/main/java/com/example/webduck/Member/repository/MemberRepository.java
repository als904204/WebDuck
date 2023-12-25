package com.example.webduck.Member.repository;

import com.example.webduck.Member.entity.Member;
import com.example.webduck.Member.entity.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmailAndSocialType(String email,SocialType socialType);
}
