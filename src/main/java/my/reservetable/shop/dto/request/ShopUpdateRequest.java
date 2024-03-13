package my.reservetable.shop.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;

import java.time.LocalTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopUpdateRequest {

    private String description;
    private ShopCountryCategory countryCategory;
    private ShopStatus status;
    private LocalTime openTime;
    private LocalTime lastOrderTime;
    private String waitingYn;

    @Builder
    private ShopUpdateRequest(String description, ShopStatus status, LocalTime openTime,
                              LocalTime lastOrderTime, String waitingYn) {
        this.description = description;
        this.status = status;
        this.openTime = openTime;
        this.lastOrderTime = lastOrderTime;
        this.waitingYn = waitingYn;
    }
}
