package my.reservetable.waiting.domain;

import my.reservetable.owner.domain.Owner;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class WaitingTest {

    @DisplayName("웨이팅생성 시, 웨이팅상태는 READY이다.")
    @Test
    void createWaitingStatusReady() {
        // given
        String email = "owner@owner.com";
        Owner owner = createOwner(email);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when
        Waiting waiting = Waiting.create(shop,3L,3, 1, registeredDateTime);

        // then
        assertThat(waiting.getWaitingStatus()).isEqualTo(WaitingStatus.READY);
    }

    @DisplayName("웨이팅의 상태를 특정 상태로 변경한다.")
    @Test
    void changeWaitingStatus() {
        // given
        String email = "owner@owner.com";
        Owner owner = createOwner(email);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");

        LocalDateTime registeredDateTime = LocalDateTime.now();
        Waiting waiting = Waiting.create(shop,3L,3, 1, registeredDateTime);

        //when
        waiting.changeWaitingStatus(WaitingStatus.VISITED);

        // then
        assertThat(waiting.getWaitingStatus()).isEqualTo(WaitingStatus.VISITED);
    }


    private Owner createOwner(String email){
        return Owner.builder()
                .nickName("사장님A")
                .password("1111")
                .email(email)
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