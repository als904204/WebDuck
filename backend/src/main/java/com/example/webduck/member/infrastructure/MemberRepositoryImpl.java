package com.example.webduck.member.infrastructure;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.service.port.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        MemberEntity entity = memberJpaRepository.save(MemberEntity.from(member));
        return entity.toModel();
    }

    @Override
    public Optional<Member> findByEmailAndSocialType(String email, SocialType socialType) {
        Optional<MemberEntity> entity = memberJpaRepository.findByEmailAndSocialType(
            email, socialType);

        return entity.map(MemberEntity::toModel);
    }

    @Override
    public Optional<Member> findById(Long id) {
        Optional<MemberEntity> entity = memberJpaRepository.findById(id);
        return entity.map(MemberEntity::toModel);
    }

    @Override
    public Member getById(Long id) {
        return findById(id).orElseThrow(
            () -> new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND));
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        Optional<MemberEntity> entity = memberJpaRepository.findByUsername(username);
        return entity.map(MemberEntity::toModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return memberJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsById(Long id) {
        return memberJpaRepository.existsById(id);
    }

}
