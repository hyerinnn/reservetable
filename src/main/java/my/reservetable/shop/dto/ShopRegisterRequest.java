package my.reservetable.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import my.reservetable.owner.domain.Owner;
import my.reservetable.shop.domain.Shop;

@Builder
@Getter
@AllArgsConstructor
public class ShopRegisterRequest {

    private String shopName;
    private Owner owner;

    public Shop toEntity(Owner owner){
        return Shop.builder()
                .name(shopName)
                .owner(owner)
                .build();
    }

}
