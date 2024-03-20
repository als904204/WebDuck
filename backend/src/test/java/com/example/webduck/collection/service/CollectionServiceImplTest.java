package com.example.webduck.collection.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.example.webduck.collection.domain.Collection;
import com.example.webduck.collection.domain.CollectionCreate;
import com.example.webduck.collection.domain.CollectionUpdate;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.infrastructure.Role;
import com.example.webduck.member.infrastructure.SocialType;
import com.example.webduck.mock.collection.FakeCollectionRepository;
import com.example.webduck.mock.collection.FakeCollectionWebtoonsRepository;
import com.example.webduck.mock.member.FakeMemberRepository;
import com.example.webduck.mock.webtoon.FakeWebtoonRepository;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CollectionServiceImplTest {

    private CollectionServiceImpl collectionService;

    @BeforeEach
    void init() {
        FakeMemberRepository fakeMemberRepository = new FakeMemberRepository();
        FakeWebtoonRepository fakeWebtoonRepository = new FakeWebtoonRepository();
        FakeCollectionRepository fakeCollectionRepository = new FakeCollectionRepository();
        FakeCollectionWebtoonsRepository fakeCollectionWebtoonsRepository = new FakeCollectionWebtoonsRepository();

        collectionService = CollectionServiceImpl.builder()
            .memberRepository(fakeMemberRepository)
            .webtoonRepository(fakeWebtoonRepository)
            .collectionRepository(fakeCollectionRepository)
            .collectionWebtoonsRepository(fakeCollectionWebtoonsRepository)
            .build();

        // 보관함
        fakeCollectionRepository.save(Collection.builder()
            .id(1L)
            .title("collection1")
            .description("hello1")
            .isOwner(true)
            .ownerId(1L)
            .ownerName("WebDuck")
            .build());

        // 회원
        fakeMemberRepository.save(Member.builder()
            .id(1L)
            .socialPk("pk")
            .socialId("id")
            .socialType(SocialType.GOOGLE)
            .role(Role.USER)
            .username("WebDuck")
            .build());

        // 웹툰
        fakeWebtoonRepository.save(Webtoon.builder()
            .id(1L)
            .title("title1")
            .summary("summary1")
            .originalImageName("name1")
            .platform(Platform.NAVER)
            .publishDay(PublishDay.MONDAY)
            .webtoonUrl("url1")
            .author("author1")
            .reviewCount(5)
            .author("WebDuck1")
            .build());

        fakeWebtoonRepository.save(Webtoon.builder()
            .id(2L)
            .title("title2")
            .summary("summary2")
            .originalImageName("name2")
            .platform(Platform.NAVER)
            .publishDay(PublishDay.MONDAY)
            .webtoonUrl("url2")
            .author("author2")
            .reviewCount(5)
            .author("WebDuck2")
            .build());

        fakeWebtoonRepository.save(Webtoon.builder()
            .id(3L)
            .title("title3")
            .summary("summary3")
            .originalImageName("name3")
            .platform(Platform.NAVER)
            .publishDay(PublishDay.MONDAY)
            .webtoonUrl("url3")
            .author("author3")
            .reviewCount(5)
            .author("WebDuck3")
            .build());
    }

    @DisplayName("성공 : 모두 조회 ")
    @Test
    void findAll() {
        Member member = Member.builder()
            .id(1L)
            .socialPk("pk")
            .socialId("id")
            .socialType(SocialType.GOOGLE)
            .role(Role.USER)
            .username("WebDuck")
            .build();
        SessionMember sessionMember = new SessionMember(member);


        List<Collection> collections = collectionService.findAll(sessionMember);
        assertThat(collections.size()).isEqualTo(1);
        assertThat(collections.get(0).getId()).isEqualTo(1L);
        assertThat(collections.get(0).getOwnerName()).isEqualTo("WebDuck");
        assertThat(collections.get(0).isOwner()).isEqualTo(true);
    }

    @DisplayName("성공 : 단건 조회")
    @Test
    void findById() {
        Collection collection = collectionService.findById(1L);
        assertThat(collection.getTitle()).isEqualTo("collection1");
    }

    @DisplayName("성공 : 보관함 생성")
    @Test
    void create() {
        Member member = Member.builder()
            .id(1L)
            .socialPk("pk")
            .socialId("id")
            .socialType(SocialType.GOOGLE)
            .role(Role.USER)
            .username("WebDuck")
            .build();
        SessionMember sessionMember = new SessionMember(member);

        List<Long> webtoonIds = List.of(1L, 2L, 3L);

        CollectionCreate collectionCreate = new CollectionCreate(
            "먼치킨 컬렉션",
            "힘숨찐 웹툰들",
            webtoonIds
        );

        Collection collection = collectionService.create(sessionMember, collectionCreate);

        assertThat(collection.getId()).isEqualTo(1L);
        assertThat(collection.getTitle()).isEqualTo("먼치킨 컬렉션");
        assertThat(collection.getDescription()).isEqualTo("힘숨찐 웹툰들");
        assertThat(collection.getOwnerName()).isEqualTo("WebDuck");
        assertThat(collection.getOwnerId()).isEqualTo(1L);
    }

    @DisplayName("성공 : 보관함 업데이트")
    @Test
    void update() {

        Member member = Member.builder()
            .id(1L)
            .socialPk("pk")
            .socialId("id")
            .socialType(SocialType.GOOGLE)
            .role(Role.USER)
            .username("WebDuck")
            .build();
        SessionMember sessionMember = new SessionMember(member);

        List<Long> webtoonIds = List.of(1L);
        CollectionUpdate collectionUpdate = new CollectionUpdate("수정","수정수정",webtoonIds);

        Long collectionId = 1L;

        Collection update = collectionService.update(sessionMember, collectionUpdate, collectionId);

        assertThat(update.getOwnerId()).isEqualTo(1L);
        assertThat(update.getId()).isEqualTo(1L);
        assertThat(update.getTitle()).isEqualTo("수정");
        assertThat(update.getDescription()).isEqualTo("수정수정");
    }

    @DisplayName("성공 : 삭제")
    @Test
    void deleteById() {
        Member member = Member.builder()
            .id(1L)
            .socialPk("pk")
            .socialId("id")
            .socialType(SocialType.GOOGLE)
            .role(Role.USER)
            .username("WebDuck")
            .build();
        SessionMember sessionMember = new SessionMember(member);

        collectionService.deleteById(sessionMember, 1L);

        List<Collection> result = collectionService.findAll(null);
        assertThat(result).isEmpty();

    }

}