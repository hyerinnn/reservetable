package my.reservetable.waiting.service;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.repository.OwnerRepository;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.request.WaitingRegisterRequest;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class WaitingServiceTest extends IntegrationTestSupport {

    @Autowired WaitingService waitingService;
    @Autowired WaitingByUserService waitingByUserService;
    @Autowired WaitingRepository waitingRepository;
    @Autowired ShopRepository shopRepository;
    @Autowired OwnerRepository ownerRepository;

    @DisplayName("웨이팅 상태를 변경한다.")
    @Test
    void changeWaitingStatus() {
        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime = LocalDateTime.of(date, LocalTime.of(17, 30, 2));

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .userId(4L)
                .shopId(1L)
                .headCount(5)
                .registeredDateTime(registeredDateTime)
                .build();
        waitingByUserService.registerWaiting(request);

        // when
        WaitingResponse changeWaitingStatue = waitingService.changeWaitingStatus(1L,WaitingStatus.NOSHOW);

        // then
        assertThat(changeWaitingStatue.getWaitingStatus()).isEqualTo(WaitingStatus.NOSHOW);
    }

    private Owner createOwner(String ownerId){
        return Owner.builder()
                .ownerId(ownerId)
                .nickName("사장님A")
                .password("1111")
                .email("abc@abc.com")
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