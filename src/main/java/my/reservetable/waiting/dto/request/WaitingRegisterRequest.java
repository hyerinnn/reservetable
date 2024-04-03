package my.reservetable.waiting.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.shop.domain.Shop;
import my.reservetable.waiting.domain.Waiting;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitingRegisterRequest {

    @NotNull(message = "userId는 필수입니다.")
    private Long userId;
    @NotNull(message = "shopId는 필수입니다.")
    private Long shopId;
    @NotNull(message = "인원수는 필수입니다.")
    private int headCount;
    @NotNull(message = "예약생성시간은 필수입니다.")
    private LocalDateTime registeredDateTime;

    @Builder
    private WaitingRegisterRequest(Long userId, Long shopId, int headCount, LocalDateTime registeredDateTime) {
        this.userId = userId;
        this.shopId = shopId;
        this.headCount = headCount;
        this.registeredDateTime = registeredDateTime;
    }

    public Waiting toEntity(Shop shop, int waitingNumber){
        return Waiting.builder()
                .shop(shop)
                .userId(userId)
                .headCount(headCount)
                .waitingNumber(waitingNumber)
                .registeredDateTime(registeredDateTime)
                .build();
    }
}
