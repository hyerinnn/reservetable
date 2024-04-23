package my.reservetable.waiting.service;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.response.WaitingOwnerResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WaitingByOwnerServiceTest extends IntegrationTestSupport {

    @Autowired WaitingByOwnerService waitingByOwnerService;
    @Autowired WaitingRepository waitingRepository;
    @Autowired ShopRepository shopRepository;
    @Autowired MemberRepository memberRepository;

    Member owner;
    Member user1, user2, user3, user4;
    Shop shop;
    Waiting waiting1, waiting2, waiting3, waiting4;

    @BeforeEach
    void setUp(){
        owner = createMember("owner@owner.com", MemberRole.OWNER);
        memberRepository.save(owner);

        shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        user1 = createMember("user1@user.com", MemberRole.USER);
        user2 = createMember("user2@user.com", MemberRole.USER);
        user3 = createMember("user3@user.com", MemberRole.USER);
        user4 = createMember("user4@user.com", MemberRole.USER);
        memberRepository.saveAll(List.of(owner, user1, user2, user3, user4));

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(17, 30, 4));
        LocalDateTime registeredDateTime3 = LocalDateTime.of(date, LocalTime.of(17, 33, 48));
        LocalDateTime registeredDateTime4 = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
         waiting1 = Waiting.create(shop, user1, 2, 1, registeredDateTime1);
         waiting2 = Waiting.create(shop, user2, 3, 2, registeredDateTime2);
         waiting3 = Waiting.create(shop, user3, 4, 3, registeredDateTime3);
         waiting4 = Waiting.create(shop, user4, 4, 4, registeredDateTime4);

        waitingRepository.saveAll(List.of(waiting1, waiting2, waiting3,waiting4));
        waiting1.changeWaitingStatus(WaitingStatus.VISITED);
    }

    @DisplayName("현재 남은 웨이팅 수를 조회한다.")
    @Test
    void getCountNowWaitingsByShopId() {
        // given
        // when
        int waitingCount = waitingByOwnerService.getCountNowWaitingsByShopId(shop.getShopId());

        // then
        assertThat(waitingCount).isEqualTo(3);
    }

    @DisplayName("현재 남은 웨이팅 목록을 조회한다.")
    @Test
    void getNowWaitingsByShopId() {
        // given
        // when
        List<WaitingOwnerResponse> waitings = waitingByOwnerService.getNowWaitingsByShopId(shop.getShopId());

        // then
        assertThat(waitings).hasSize(3)
                .extracting("waitingNumber")
                .doesNotContain(1);
    }

    private Member createMember(String email, MemberRole role){
        return Member.builder()
                .nickName("뉴멤버")
                .password("1111")
                .email(email)
                .phoneNumber("01027374848")
                .role(role)
                .build();
    }

    private Shop createShop(Member ownerMember, String description, ShopCountryCategory countryCategory,
                            ShopStatus status, LocalTime openTime, LocalTime lastOrderTime, String waitingYn){
        return Shop.builder()
                .shopName("테스트용식당")
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