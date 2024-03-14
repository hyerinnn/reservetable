package my.reservetable.shop.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.owner.domain.Owner;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopRegisterRequest {

    @NotBlank(message = "식당 이름은 필수입니다.")
    private String shopName;
    @NotBlank(message = "ownerId는 필수입니다.")
    private String ownerId;
    private String shopNumber;
    private Address address;
    private String description;
    private ShopCountryCategory countryCategory;
    private ShopStatus status = ShopStatus.READY;
    private LocalTime openTime;
    private LocalTime lastOrderTime;
    private String waitingYn;

    public Shop toEntity(Owner owner){
        return Shop.builder()
                .shopName(shopName)
                .owner(owner)
                .shopNumber(shopNumber)
                .address(address)
                .description(description)
                .countryCategory(countryCategory)
                .status(status)
                .openTime(openTime)
                .lastOrderTime(lastOrderTime)
                .waitingYn(waitingYn)
                .build();
    }

    @Builder
    private ShopRegisterRequest(String shopName, String ownerId, String shopNumber, Address address, String description,
                               ShopCountryCategory countryCategory, ShopStatus status, LocalTime openTime, LocalTime lastOrderTime,
                               String waitingYn) {
        this.shopName = shopName;
        this.ownerId = ownerId;
        this.shopNumber = shopNumber;
        this.address = address;
        this.description = description;
        this.countryCategory = countryCategory;
        this.status = status;
        this.openTime = openTime;
        this.lastOrderTime = lastOrderTime;
        this.waitingYn = waitingYn;
    }
}
