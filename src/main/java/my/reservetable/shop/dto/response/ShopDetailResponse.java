package my.reservetable.shop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopDetailResponse {

    private Long shopId;
    private String shopName;
    private String shopNumber;
    private Address address;
    private String description;
    private ShopCountryCategory countryCategory;
    private ShopStatus status;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime openTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime lastOrderTime;
    private String waitingYn;
    private int likeCnt;

    @Builder
    private ShopDetailResponse(Long shopId, String shopName, String shopNumber, Address address,
                               String description, ShopCountryCategory countryCategory, ShopStatus status,
                               LocalTime openTime, LocalTime lastOrderTime, String waitingYn, int likeCnt) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopNumber = shopNumber;
        this.address = address;
        this.description = description;
        this.countryCategory = countryCategory;
        this.status = status;
        this.openTime = openTime;
        this.lastOrderTime = lastOrderTime;
        this.waitingYn = waitingYn;
        this.likeCnt = likeCnt;

    }

    public static ShopDetailResponse toDto(Shop shop){
        return ShopDetailResponse.builder()
                .shopId(shop.getShopId())
                .shopName(shop.getShopName())
                .shopNumber(shop.getShopNumber())
                .address(shop.getAddress())
                .description(shop.getDescription())
                .countryCategory(shop.getCountryCategory())
                .status(shop.getStatus())
                .openTime(shop.getOpenTime())
                .lastOrderTime(shop.getLastOrderTime())
                .waitingYn(shop.getWaitingYn())
                .likeCnt(shop.getLikeCnt())
                .build();
    }
}
