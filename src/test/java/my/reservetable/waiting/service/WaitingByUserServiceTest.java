package my.reservetable.waiting.service;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.exception.NoRegisterWaitingException;
import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.repository.OwnerRepository;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import my.reservetable.waiting.dto.request.MyWaitingRequest;
import my.reservetable.waiting.dto.request.WaitingRegisterRequest;
import my.reservetable.waiting.dto.request.WaitingStatusUpdateRequest;
import my.reservetable.waiting.dto.response.WaitingResponse;
import my.reservetable.waiting.repository.WaitingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@SpringBootTest
//@Transactional
class WaitingByUserServiceTest extends IntegrationTestSupport {

    @Autowired
    WaitingByUserService waitingService;
    @Autowired
    WaitingRepository waitingRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @DisplayName("웨이팅을 성공적으로 생성한다")
    @Test
    void registerWaiting() {

        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10, 00), LocalTime.of(21, 00), "Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(17, 30, 4));
        LocalDateTime registeredDateTime3 = LocalDateTime.of(date, LocalTime.of(17, 33, 48));
        LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));

        Waiting waiting1 = Waiting.create(shop, 1L, 2, registeredDateTime1);
        Waiting waiting2 = Waiting.create(shop, 2L, 3, registeredDateTime2);
        Waiting waiting3 = Waiting.create(shop, 3L, 4, registeredDateTime3);
        waitingRepository.saveAll(List.of(waiting1, waiting2, waiting3));
        waiting1.changeWaitingStatus(WaitingStatus.VISITED);

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .userId(4L)
                .shopId(1L)
                .headCount(5)
                .registeredDateTime(targetDateTime)
                .build();

        // when
        WaitingResponse waitingResponse = waitingService.registerWaiting(request);

        // then
        assertThat(waitingResponse.getWaitingId()).isEqualTo(4);
        assertThat(waitingResponse.getMyWaitingOrder()).isEqualTo(3);
        assertThat(waitingResponse.getWaitingNumber()).isEqualTo(4);
    }

    @DisplayName("가게의 status가 open이 아닌 경우, 웨이팅 등록에 실패한다.")
    @Test
    void registerWaitingFail1() {

        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.BREAK_TIME,
                LocalTime.of(10, 00), LocalTime.of(21, 00), "Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime alreadyWaitingTime = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime newWaitingTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));

        Waiting waiting = Waiting.create(shop, 1L, 2, alreadyWaitingTime);
        waitingRepository.save(waiting);

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .userId(1L)
                .shopId(1L)
                .headCount(5)
                .registeredDateTime(newWaitingTime)
                .build();

        // when & then
        assertThatThrownBy(() -> waitingService.registerWaiting(request))
                .isInstanceOf(NoRegisterWaitingException.class)
                .hasMessage("웨이팅을 할 수 없습니다.");
    }

    @DisplayName("이미 웨이팅중인 회원은 같은 가게에 웨이팅 등록에 실패한다.")
    @Test
    void registerWaitingFail2() {

        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10, 00), LocalTime.of(21, 00), "Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime alreadyWaitingTime = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime newWaitingTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));

        Waiting waiting1 = Waiting.create(shop, 1L, 2, alreadyWaitingTime);
        waitingRepository.save(waiting1);

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .userId(1L)
                .shopId(1L)
                .headCount(5)
                .registeredDateTime(newWaitingTime)
                .build();

        // when & then
        assertThatThrownBy(() -> waitingService.registerWaiting(request))
                .isInstanceOf(NoRegisterWaitingException.class)
                .hasMessage("이미 웨이팅한 회원입니다.");
    }

    @DisplayName("나의 현재 웨이팅을 조회한다.")
    @Test
    void getMyWaiting() {

        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10, 00), LocalTime.of(21, 00), "Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .userId(4L)
                .shopId(1L)
                .headCount(5)
                .registeredDateTime(targetDateTime)
                .build();
        WaitingResponse waitingResponse = waitingService.registerWaiting(request);

        // when
        MyWaitingRequest myWaiting = MyWaitingRequest.builder()
                .shopId(waitingResponse.getShopId())
                .userId(waitingResponse.getUserId())
                .build();
        WaitingResponse myWaitingResponse = waitingService.getMyNowWaiting(myWaiting);

        // then
        assertThat(myWaitingResponse.getWaitingId()).isEqualTo(waitingResponse.getWaitingId());
    }

    @DisplayName("나의 모든 웨이팅을 조회한다.")
    @Test
    void getMyWaitings() {

        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10, 00), LocalTime.of(21, 00), "Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(18, 10, 00));
        Waiting waiting1 = Waiting.create(shop,1L,2, registeredDateTime1);
        Waiting waiting2 = Waiting.create(shop,1L,3, registeredDateTime2);
        waitingRepository.saveAll(List.of(waiting1,waiting2));

        // when
        List<WaitingResponse> myWaitingResponse = waitingService.getAllMyWaitings(1L);

        // then
        assertThat(myWaitingResponse).hasSize(2)
                .extracting("headCount")
                .contains(2,3);
    }

    @DisplayName("웨이팅 상태를 변경한다.")
    @Test
    void changeWaitingStatus() {
        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10, 00), LocalTime.of(21, 00), "Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime = LocalDateTime.of(date, LocalTime.of(17, 30, 2));

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .userId(4L)
                .shopId(1L)
                .headCount(5)
                .registeredDateTime(registeredDateTime)
                .build();
        waitingService.registerWaiting(request);

        WaitingStatusUpdateRequest updateRequest = WaitingStatusUpdateRequest.builder()
                .waitingId(1L)
                .waitingStatus(WaitingStatus.NOSHOW)
                .build();

        // when
        WaitingResponse changeWaitingStatue = waitingService.changeWaitingStatus(updateRequest);

        // then
        assertThat(changeWaitingStatue.getWaitingStatus()).isEqualTo(WaitingStatus.NOSHOW);
    }


    private Owner createOwner(String ownerId) {
        return Owner.builder()
                .ownerId(ownerId)
                .nickName("사장님A")
                .password("1111")
                .email("abc@abc.com")
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