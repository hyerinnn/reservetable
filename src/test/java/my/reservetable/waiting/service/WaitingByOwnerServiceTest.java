package my.reservetable.waiting.service;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.repository.OwnerRepository;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.response.WaitingOwnerResponse;
import my.reservetable.waiting.repository.WaitingRepository;
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
    @Autowired OwnerRepository ownerRepository;

    @DisplayName("현재 남은 웨이팅 수를 조회한다.")
    @Test
    void getCountNowWaitingsByShopId() {
        // given
        String email = "owner@owner.com";
        Owner owner = createOwner(email);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10, 00), LocalTime.of(21, 00), "Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(17, 30, 4));
        LocalDateTime registeredDateTime3 = LocalDateTime.of(date, LocalTime.of(17, 33, 48));
        LocalDateTime registeredDateTime4 = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
        Waiting waiting1 = Waiting.create(shop, 1L, 2, 1, registeredDateTime1);
        Waiting waiting2 = Waiting.create(shop, 2L, 3, 2, registeredDateTime2);
        Waiting waiting3 = Waiting.create(shop, 3L, 4, 3, registeredDateTime3);
        Waiting waiting4 = Waiting.create(shop, 3L, 4, 4, registeredDateTime4);

        waitingRepository.saveAll(List.of(waiting1, waiting2, waiting3,waiting4));
        waiting1.changeWaitingStatus(WaitingStatus.VISITED);

        // when
        int waitingCount = waitingByOwnerService.getCountNowWaitingsByShopId(shop.getShopId());

        // then
        assertThat(waitingCount).isEqualTo(3);
    }

    @DisplayName("현재 남은 웨이팅 목록을 조회한다.")
    @Test
    void getNowWaitingsByShopId() {
        // given
        String email = "owner@owner.com";
        Owner owner = createOwner(email);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10, 00), LocalTime.of(21, 00), "Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(17, 30, 4));
        LocalDateTime registeredDateTime3 = LocalDateTime.of(date, LocalTime.of(17, 33, 48));
        LocalDateTime registeredDateTime4 = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
        Waiting waiting1 = Waiting.create(shop, 1L, 2, 1, registeredDateTime1);
        Waiting waiting2 = Waiting.create(shop, 2L, 3, 2, registeredDateTime2);
        Waiting waiting3 = Waiting.create(shop, 3L, 4, 3, registeredDateTime3);
        Waiting waiting4 = Waiting.create(shop, 3L, 4, 4, registeredDateTime4);

        waitingRepository.saveAll(List.of(waiting1, waiting2, waiting3,waiting4));
        waiting1.changeWaitingStatus(WaitingStatus.VISITED);

        // when
        List<WaitingOwnerResponse> waitings = waitingByOwnerService.getNowWaitingsByShopId(shop.getShopId());

        // then
        assertThat(waitings).hasSize(3)
                .extracting("waitingNumber")
                .doesNotContain(1);
    }

    private Owner createOwner(String email) {
        return Owner.builder()
                .nickName("사장님A")
                .password("1111")
                .email(email)
                .phoneNumber("01027374848")
                .build();
    }

    private Shop createShop(Owner owner, String description, ShopCountryCategory countryCategory,
                            ShopStatus status, LocalTime openTime, LocalTime lastOrderTime, String waitingYn) {
        return Shop.builder()
                .shopName("해피식당")
                .owner(owner)
                .shopNumber("02-1234-5678")
                .address(new Address("15151", "서울특별시 00로 32"))
                .description(description)
                .countryCategory(countryCategory)
                .status(status)
                .openTime(openTime)
                .lastOrderTime(lastOrderTime)
                .waitingYn(waitingYn)
                .build();
    }

}