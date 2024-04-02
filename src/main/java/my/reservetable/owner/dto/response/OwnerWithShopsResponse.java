package my.reservetable.owner.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerWithShopsResponse {
//TODO : Owner와 Shop 양방향 관계설정 시 사용예정

/*
    private String ownerId;
    private String nickName;
    private List<ShopResponse> shops;

    @Builder
    private OwnerWithShopsResponse(String ownerId, String nickName, List<ShopResponse> shops) {
        this.ownerId = ownerId;
        this.nickName = nickName;
        this.shops = shops;
    }

    // entity를 dto로 변환
    public static OwnerWithShopsResponse toDto(Owner owner){
        return OwnerWithShopsResponse.builder()
                .ownerId(owner.getOwnerId())
                .nickName(owner.getNickName())
                .shops((owner.getShops()).stream().map(ShopResponse::toDto).collect(Collectors.toList()))
                .build();
    }*/
}
