package com.example.webduck.member.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByEmailAndSocialType(String email,SocialType socialType);

    Optional<MemberEntity> findByUsername(String generateName);

    boolean existsByUsername(String username);
}
