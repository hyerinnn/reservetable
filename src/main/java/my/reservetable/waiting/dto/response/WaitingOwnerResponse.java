package my.reservetable.waiting.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitingOwnerResponse {

    private Long waitingId;
    private Long userId;
    private Long shopId;
    private String shopName;
    private int headCount;
    private WaitingStatus waitingStatus;
    private int waitingNumber;
    private LocalDateTime registeredDateTime;

    @Builder
    private WaitingOwnerResponse(Long waitingId, Long userId, Long shopId, String shopName, int headCount,
                                 WaitingStatus waitingStatus, int waitingNumber,
                                 LocalDateTime registeredDateTime) {
        this.waitingId = waitingId;
        this.userId = userId;
        this.shopId = shopId;
        this.shopName = shopName;
        this.headCount = headCount;
        this.waitingStatus = waitingStatus;
        this.waitingNumber = waitingNumber;
        this.registeredDateTime = registeredDateTime;
    }

    public static WaitingOwnerResponse toDto(Waiting waiting){
        return WaitingOwnerResponse.builder()
                .waitingId(waiting.getWaitingId())
                .userId(waiting.getMember().getId())
                .shopId(waiting.getShop().getShopId())
                .shopName(waiting.getShop().getShopName())
                .headCount(waiting.getHeadCount())
                .waitingStatus(waiting.getWaitingStatus())
                .waitingNumber(waiting.getWaitingNumber())
                .registeredDateTime(waiting.getRegisteredDateTime())
                .build();
    }
}
