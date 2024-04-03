package my.reservetable.waiting.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.shop.domain.Shop;
import my.reservetable.waiting.domain.Waiting;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyWaitingRequest {

    @NotBlank(message = "userId는 필수입니다.")
    private Long userId;
    @NotBlank(message = "shopId는 필수입니다.")
    private Long shopId;

    @Builder
    private MyWaitingRequest(Long userId, Long shopId) {
        this.userId = userId;
        this.shopId = shopId;
    }

    public Waiting toEntity(Shop shop){
        return Waiting.builder()
                .shop(shop)
                .userId(userId)
                .build();
    }
}
