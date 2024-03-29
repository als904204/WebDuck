# 연관 관계에 대한 고민

---

## 고민
- 엔티티 간 많은 연관 관계로 인해 따라 복잡성이 증가하여 연관 관계를 맺는 것이 좋은 것인지 다시 고민해보게되었습니다.


## 연관관계 문제점

```java

@Entity
public class Review extends BaseEntity {
    /**
     *  문제점 : 연관관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<ReviewLikes> likes = new ArrayList<>();

    // 그 외 코드들...
    
}


```
Review 엔티티의 경우 많은 엔티티와 연관성이 있기 때문에 다수의 엔티티들과 연관관계를 맺었습니다.

 Review 엔티티가 Member, Webtoon, ReviewLikes와 같은 여러 엔티티와 연관 관계를 맺으면서, 엔티티의 복잡성이 증가해 유지보수의 어려움을 겪었습니다.

---

## 해결
- 그래서 주변 개발자분에게 자문을 구하고, 각종 개발 커뮤니티 검색을 통해 "연관관계를 제거하고 FK를 참조하는 방식으로 해보는건 어떠냐" 라는 의견을 들었습니다.


- 연관관계를 제거하고, 외래 키 값을 직접 필드값으로 갖게 했습니다.
```java
@Table(name = "review")
@Entity
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long webtoonId;

    @Column(nullable = false)
    private Long memberId;
}
```
@OneToMany로 연결되어있던 연관 관계들도 삭제 되었고, 엔티티의 책임이 가벼워졌습니다.

--- 
## 사이드 이펙트
연관 관계를 제거함에 따라 Webtoon 에 해당하는 Review 목록을 조회하는 방식이 객체 참조로 가져오는 방식에서 직접 쿼리를 작성해야하는 사이드 이펙트가 발생했습니다.


## 해결 
아래와 같이 QueryDsl 이용해 innerJoin 후, 원하는 컬럼만 쿼리하는 식으로 변경하여 성능도 개선하게 되었습니다.

```java
class ReviewCustomRepositoryImpl{
    
    List<ReviewSliceResponse> content = queryFactory.select(
            Projections.constructor(ReviewSliceResponse.class,
                review.id,
                review.content,
                review.reviewerNickname,
                review.memberId,
                review.rating,
                review.createdAt,
                review.likesCount
            ))
        .from(review)
        .innerJoin(webtoon).on(review.webtoonId.eq(webtoon.id))
        .orderBy(review.id.desc())
        .fetch(); 
}

```
---

## 느낀점
> 엔티티 간의 연관 관계를 제거함으로써, 엔티티의 구조가 단순해져 이해하기 쉬워졌고,
QueryDsl을 사용하여 필요한 데이터만 조회하는 방식으로 변경함으로써, 성능 또한 개선되었습니다. 
>
> 하지만 연관 관계를 제거함으로써 JPA가 제공하는 객체 그래프 탐색 같은 ORM의 일부 기능을 사용하지 못하게 되었고, 어떻게 보면 QueryDsl을 사용함에 따라 다른 부분에서 복잡성이 증가했습니다.
> 
> 적용한 리팩토링 방향은 기술 스택이 추가됨에 따른 단점은 있지만, 제가 생각하는 좋은 애플리케이선 설계란 장기적인 관점에선 변경과 확장에 용이하고,유지보수가 용이한 애플리케이션이 중요하다고 생각합니다.



