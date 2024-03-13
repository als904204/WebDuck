package com.example.webduck.member.infrastructure;

import com.example.webduck.global.common.BaseTime;
import com.example.webduck.global.converter.EncryptorConverter;
import com.example.webduck.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Table(name = "member")
@Getter
@Entity
public class MemberEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long id;

    @Length(min = 2, max = 16)
    @Column(nullable = false, length = 16,unique = true)
    private String username;

    @Convert(converter = EncryptorConverter.class)
    @Column(nullable = false,unique = true)
    private String email;

    @Convert(converter = EncryptorConverter.class)
    @Column(nullable = false)
    private String socialPk;

    @Convert(converter = EncryptorConverter.class)
    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // 이전 로그인 시간
    private LocalDateTime previousLoginAt;

    // 현재 로그인 시간
    private LocalDateTime currentLoginAt;

    protected MemberEntity() {
    }


    @Builder
    public MemberEntity(Long id, String username, String email, Role role, SocialType socialType,
        String socialId, String socialPk,LocalDateTime currentLoginAt, LocalDateTime previousLoginAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.socialPk = socialPk;
        this.socialId = socialId;
        this.socialType = socialType;
        this.currentLoginAt = currentLoginAt;
        this.previousLoginAt = previousLoginAt;
    }

    // Domain to Entity
    public static MemberEntity from(Member member) {
        return MemberEntity.builder()
            .id(member.getId())
            .username(member.getUsername())
            .email(member.getEmail())
            .role(member.getRole())
            .socialType(member.getSocialType())
            .socialId(member.getSocialId())
            .socialPk(member.getSocialPk())
            .currentLoginAt(member.getCurrentLoginAt())
            .previousLoginAt(member.getPreviousLoginAt())
            .build();
    }

    // to Domain
    public Member toModel() {
        return Member.builder()
            .id(id)
            .username(username)
            .email(email)
            .role(role)
            .socialType(socialType)
            .socialId(socialId)
            .socialPk(socialPk)
            .currentLoginAt(currentLoginAt)
            .previousLoginAt(previousLoginAt)
            .build();
    }

}