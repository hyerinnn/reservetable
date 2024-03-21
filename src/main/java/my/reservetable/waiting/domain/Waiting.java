package my.reservetable.waiting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.config.AuditingEntity;
import my.reservetable.shop.domain.Shop;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Waiting extends AuditingEntity {

    // 최대 웨이팅수
    private static final int MAX_WAITING = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waitingId;

    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

    //private User user;    //TODO : 일반회원
    private Long userId;

    private WaitingStatus waitingStatus;

    private int headCount;  //인원수

    private LocalDateTime registeredDateTime;


    @Builder
    private Waiting(Shop shop, Long userId, int headCount, LocalDateTime registeredDateTime) {
        this.shop = shop;
        userId = userId;
        this.headCount = headCount;
        this.registeredDateTime = registeredDateTime;
    }
    public static Waiting create(Shop shop, Long userId, int headCount, LocalDateTime registeredDateTime){
        return Waiting.builder()
                .shop(shop)
                .userId(userId)
                .headCount(headCount)
                .registeredDateTime(registeredDateTime)
                .build();
    }
/*
    public Waiting(Shop shop, Long userId, int headCount, LocalDateTime registeredDateTime) {
        this.shop = shop;
        this.userId = userId;
        this.headCount = headCount;
        this.waitingStatus = WaitingStatus.READY;
        this.registeredDateTime = registeredDateTime;
    }

    public static Waiting create(Shop shop, Long userId, int headCount, LocalDateTime registeredDateTime){
        return new Waiting(shop,userId, headCount, registeredDateTime);
    }
*/
    public void changeWaitingStatus(WaitingStatus waitingStatus){
        this.waitingStatus = waitingStatus;
    }
}
