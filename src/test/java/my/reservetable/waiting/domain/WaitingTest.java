package my.reservetable.waiting.domain;

import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
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
        Member owner = createOwnerMember("owner@owner.com");
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
        Member owner = createOwnerMember("owner@owner.com");

        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");

        LocalDateTime registeredDateTime = LocalDateTime.now();
        Waiting waiting = Waiting.create(shop,3L,3, 1, registeredDateTime);

        //when
        waiting.changeWaitingStatus(WaitingStatus.VISITED);

        // then
        assertThat(waiting.getWaitingStatus()).isEqualTo(WaitingStatus.VISITED);
    }


    private Member createOwnerMember(String email){
        return Member.builder()
                .nickName("사장님A")
                .password("1111")
                .email(email)
                .phoneNumber("01027374848")
                .role(MemberRole.OWNER)
                .build();
    }

    private Shop createShop(Member ownerMember, String description, ShopCountryCategory countryCategory,
                            ShopStatus status, LocalTime openTime, LocalTime lastOrderTime, String waitingYn){
        return Shop.builder()
                .shopName("해피식당")
                .member(ownerMember)
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