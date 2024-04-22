package my.reservetable.shop.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopResponse {

    private Long shopId;
    private String shopName;
    private Address address;
    private String description;
    private ShopCountryCategory countryCategory;

    @Builder
    private ShopResponse(Long shopId, String shopName, Address address,
                         String description, ShopCountryCategory countryCategory) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.address = address;
        this.description = description;
        this.countryCategory = countryCategory;
    }

    public static ShopResponse toDto(Shop shop){
        return ShopResponse.builder()
                .shopId(shop.getShopId())
                .shopName(shop.getShopName())
                .address(shop.getAddress())
                .description(shop.getDescription())
                .countryCategory(shop.getCountryCategory())
                .build();
    }
}
