package my.reservetable.like;

import my.reservetable.comon.dummy.DummyObject;
import my.reservetable.owner.domain.Owner;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class LikeTest  extends DummyObject {

    @DisplayName("")
    @Test
    void test() {
        // given
        Owner owner = createOwner();
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");

        Likes like = Likes.builder()
                .memberId(1L)
                .shop(shop)
                .build();

        // when


        // then
    }

    private Owner createOwner(){
        return Owner.builder()
                .nickName("사장님A")
                .password("1111")
                .email("owner@owner.com")
                .phoneNumber("01027374848")
                .build();
    }

    private Shop createShop(Owner owner, String description, ShopCountryCategory countryCategory,
                            ShopStatus status, LocalTime openTime, LocalTime lastOrderTime, String waitingYn){
        return Shop.builder()
                .shopName("해피식당")
                .owner(owner)
                .shopNumber("02-1234-5678")
                .address(new Address("15151","서울특별시 00로 32"))
                .description(description)
                .countryCategory(countryCategory)
                .status(status)
                .openTime(openTime)
                .lastOrderTime(lastOrderTime)
                .waitingYn(waitingYn)
                .build();
    }
}