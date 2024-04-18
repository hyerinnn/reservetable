package my.reservetable.like;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.config.AuditingEntity;
import my.reservetable.shop.domain.Shop;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends AuditingEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private Shop shop;

/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private Member member;
*/

    private Long memberId;

    @Builder
    private Likes(Long id, Shop shop, Long memberId) {
        this.id = id;
        this.shop = shop;
        this.memberId = memberId;
    }

    // == 연관관계 메서드 == //
    public void setShop(Shop shop) {
        this.shop = shop;
        shop.getLikes().add(this);
    }

}
