package my.reservetable.like.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.like.domain.Likes;
import my.reservetable.shop.dto.response.ShopResponse;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeResponse {

    private Long id;
    private ShopResponse shop;

    @Builder
    private LikeResponse(Long id, ShopResponse shop) {
        this.id = id;
        this.shop = shop;
    }

    public static LikeResponse toDto(Likes likes){
        return LikeResponse.builder()
                .id(likes.getId())
                .shop(ShopResponse.toDto(likes.getShop()))
                .build();
    }
}
