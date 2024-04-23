package my.reservetable.waiting.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.member.domain.Member;
import my.reservetable.shop.domain.Shop;
import my.reservetable.waiting.domain.Waiting;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyWaitingRequest {

    @NotNull(message = "userId는 필수입니다.")
    private Long userId;
    @NotNull(message = "shopId는 필수입니다.")
    private Long shopId;

    @Builder
    private MyWaitingRequest(Long userId, Long shopId) {
        this.userId = userId;
        this.shopId = shopId;
    }

    public Waiting toEntity(Shop shop, Member member){
        return Waiting.builder()
                .shop(shop)
                .member(member)
                .build();
    }
}
