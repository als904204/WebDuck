package com.example.webduck.member.repository;

import com.example.webduck.member.entity.Member;
import com.example.webduck.member.entity.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmailAndSocialType(String email,SocialType socialType);

    Optional<Member> findByUsername(String generateName);
}
