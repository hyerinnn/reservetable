package my.reservetable.like.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long shopId;

    @Builder
    private LikeRequest(Long memberId, Long shopId) {
        this.memberId = memberId;
        this.shopId = shopId;
    }
}
