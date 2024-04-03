package my.reservetable.shop.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.dto.response.OwnerForShopResponse;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopForOwnerResponse {

    private Long shopId;
    private String shopName;
    //private Owner owner;
    private OwnerForShopResponse owner;
    private String shopNumber;
    private Address address;
    private String description;
    private ShopCountryCategory countryCategory;
    private ShopStatus status;
    private LocalTime openTime;
    private LocalTime lastOrderTime;
    private String waitingYn;

    @Builder
    private ShopForOwnerResponse(Long shopId, String shopName, Owner owner, String shopNumber, Address address,
                                 String description, ShopCountryCategory countryCategory, ShopStatus status,
                                 LocalTime openTime, LocalTime lastOrderTime, String waitingYn) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.owner = OwnerForShopResponse.toDto(owner);
        this.shopNumber = shopNumber;
        this.address = address;
        this.description = description;
        this.countryCategory = countryCategory;
        this.status = status;
        this.openTime = openTime;
        this.lastOrderTime = lastOrderTime;
        this.waitingYn = waitingYn;

    }

    public static ShopForOwnerResponse toDto(Shop shop){
        return ShopForOwnerResponse.builder()
                .shopId(shop.getShopId())
                .shopName(shop.getShopName())
                .owner(shop.getOwner())
                .shopNumber(shop.getShopNumber())
                .address(shop.getAddress())
                .description(shop.getDescription())
                .countryCategory(shop.getCountryCategory())
                .status(shop.getStatus())
                .openTime(shop.getOpenTime())
                .lastOrderTime(shop.getLastOrderTime())
                .waitingYn(shop.getWaitingYn())
                .build();
    }
}
