package my.reservetable.waiting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.config.AuditingEntity;
import my.reservetable.shop.domain.Shop;

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
    private Long UserId;

    private WaitingStatus waitingStatus;

    private int headCount;  //인원수

}
