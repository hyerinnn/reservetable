package my.reservetable.shop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.config.AuditingEntity;
import my.reservetable.like.domain.Likes;
import my.reservetable.member.domain.Member;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(nullable = false)
    private String shopName;

    //@Column(nullable = false)
    private String shopNumber;

    //@Column(nullable = false)
    @Embedded
    private Address address;

    //@Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopCountryCategory countryCategory;        // 국가 카테고리(한식,중식 등)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopStatus status;          // 식당 상태(오픈전, 오픈, 브레이크, 마감, 휴일 등)

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime lastOrderTime;

    @Column(nullable = false)
    private String waitingYn;       // 웨이팅 오픈여부

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @Builder
    private Shop(Member member, String shopName, String shopNumber, Address address, String description, ShopCountryCategory countryCategory,
                ShopStatus status, LocalTime openTime, LocalTime lastOrderTime, String waitingYn) {
        this.member = member;
        this.shopName = shopName;
        this.shopNumber = shopNumber;
        this.address = address;
        this.description = description;
        this.countryCategory = countryCategory;
        this.status = status;
        this.openTime = openTime;
        this.lastOrderTime = lastOrderTime;
        this.waitingYn = waitingYn;
    }

    public void update(String description, ShopStatus status, LocalTime openTime, LocalTime lastOrderTime, String waitingYn) {
            this.description = description;
            this.status = status;
            this.openTime = openTime;
            this.lastOrderTime = lastOrderTime;
            this.waitingYn = waitingYn;
    }

    public void changeStatus(ShopStatus status){
        this.status = status;
    }

/*    public void addLike(Likes like) {
        like.setShop(this);
        this.likes.add(like);
    }*/
}
