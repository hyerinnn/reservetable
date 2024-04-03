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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waitingId;

    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

    //private User user;    //TODO : 일반회원
    private Long userId;

    @Enumerated(EnumType.STRING)
    private WaitingStatus waitingStatus;

    private int headCount;  //인원수

    private int waitingNumber;

    private LocalDateTime registeredDateTime;


    @Builder
    private Waiting(Shop shop, Long userId, int headCount, int waitingNumber, LocalDateTime registeredDateTime) {
        this.shop = shop;
        this.userId = userId;
        this.headCount = headCount;
        this.waitingStatus = WaitingStatus.READY;
        this.waitingNumber = waitingNumber;
        this.registeredDateTime = registeredDateTime;
    }

    public static Waiting create(Shop shop, Long userId, int headCount, int waitingNumber, LocalDateTime registeredDateTime){
        return Waiting.builder()
                .shop(shop)
                .userId(userId)
                .headCount(headCount)
                .waitingNumber(waitingNumber)
                .registeredDateTime(registeredDateTime)
                .build();
    }

    public void changeWaitingStatus(WaitingStatus waitingStatus){
        this.waitingStatus = waitingStatus;
    }
}
