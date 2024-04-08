package my.reservetable.owner.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.owner.domain.Owner;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerForShopResponse {

    private Long ownerId;
    private String nickName;

    @Builder
    private OwnerForShopResponse(Long ownerId, String nickName) {
        this.ownerId = ownerId;
        this.nickName = nickName;
    }

    public static OwnerForShopResponse toDto(Owner owner){
        return OwnerForShopResponse.builder()
                .ownerId(owner.getOwnerId())
                .nickName(owner.getNickName())
                .build();
    }
}
