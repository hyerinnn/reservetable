package my.reservetable.waiting.domain;

import my.reservetable.comon.dummy.DummyObject;
import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class WaitingTest extends DummyObject {

    @DisplayName("웨이팅생성 시, 웨이팅상태는 READY이다.")
    @Test
    void createWaitingStatusReady() {
        // given
        Member owner = newMember("owner@owner.com", MemberRole.OWNER);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        Member user = newMember("user@user.com", MemberRole.USER);

        LocalDateTime registeredDateTime = LocalDateTime.now();

        // when
        Waiting waiting = Waiting.create(shop,user,3, 1, registeredDateTime);

        // then
        assertThat(waiting.getWaitingStatus()).isEqualTo(WaitingStatus.READY);
    }

    @DisplayName("웨이팅의 상태를 특정 상태로 변경한다.")
    @Test
    void changeWaitingStatus() {
        // given
        Member owner = newMember("owner@owner.com", MemberRole.OWNER);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        Member user = newMember("user@user.com", MemberRole.USER);

        LocalDateTime registeredDateTime = LocalDateTime.now();
        Waiting waiting = Waiting.create(shop,user,3, 1, registeredDateTime);

        //when
        waiting.changeWaitingStatus(WaitingStatus.VISITED);

        // then
        assertThat(waiting.getWaitingStatus()).isEqualTo(WaitingStatus.VISITED);
    }

}