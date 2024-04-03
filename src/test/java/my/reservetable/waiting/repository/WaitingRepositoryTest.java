package my.reservetable.waiting.repository;

import my.reservetable.owner.domain.Owner;
import my.reservetable.owner.repository.OwnerRepository;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class WaitingRepositoryTest {

    @Autowired WaitingRepository waitingRepository;
    @Autowired ShopRepository shopRepository;
    @Autowired OwnerRepository ownerRepository;

    @DisplayName("[웨이팅 번호] 타겟 웨이팅생성시간보다 먼저 생성된 웨이팅 수를 조회한다.")
    @Test
    void getCountBeforeTargetWaiting() {
        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(17, 30, 4));
        LocalDateTime registeredDateTime3 = LocalDateTime.of(date, LocalTime.of(17, 33, 48));
        LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
        LocalDateTime registeredDateTime4 = LocalDateTime.of(date, LocalTime.of(17, 55, 00));
        LocalDateTime registeredDateTime5 = LocalDateTime.of(date, LocalTime.of(18, 10, 00));

        Waiting waiting1 = Waiting.create(shop,1L,2, 1, registeredDateTime1);
        Waiting waiting2 = Waiting.create(shop,2L,3, 2, registeredDateTime2);
        Waiting waiting3 = Waiting.create(shop,3L,4, 3, registeredDateTime3);
        Waiting targetWaiting = Waiting.create(shop,4L,3, 4, targetDateTime);
        Waiting waiting4 = Waiting.create(shop,4L,5, 5, registeredDateTime4);
        Waiting waiting5 = Waiting.create(shop,4L,2, 6, registeredDateTime5);

        waitingRepository.saveAll(List.of(waiting1,waiting2,waiting3,waiting4,waiting5,targetWaiting));

        // when
        int count = waitingRepository.getRegisteredDateTimeBefore(targetDateTime, date);

        // then
        assertThat(count).isEqualTo(3);
    }

    @DisplayName("[현재 나의 순서] 타겟 웨이팅생성시간보다 먼저 생성된 웨이팅 중, '입장대기'인 웨이팅 수를 조회한다.")
    @Test
    void getCountBeforeAndReadyTargetWaiting() {
        // given
        String ownerId = "test001";
        Owner owner = createOwner(ownerId);
        ownerRepository.save(owner);
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(17, 30, 4));
        LocalDateTime registeredDateTime3 = LocalDateTime.of(date, LocalTime.of(17, 33, 48));
        LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
        LocalDateTime registeredDateTime4 = LocalDateTime.of(date, LocalTime.of(17, 55, 00));
        LocalDateTime registeredDateTime5 = LocalDateTime.of(date, LocalTime.of(18, 10, 00));

        Waiting waiting1 = Waiting.create(shop,1L,2, 1, registeredDateTime1);
        Waiting waiting2 = Waiting.create(shop,2L,3, 2, registeredDateTime2);
        Waiting waiting3 = Waiting.create(shop,3L,4, 3, registeredDateTime3);
        Waiting targetWaiting = Waiting.create(shop,4L,3, 4, targetDateTime);
        Waiting waiting4 = Waiting.create(shop,4L,5, 5, registeredDateTime4);
        Waiting waiting5 = Waiting.create(shop,4L,2, 6, registeredDateTime5);
        waitingRepository.saveAll(List.of(waiting1,waiting2,waiting3,waiting4,waiting5,targetWaiting));

        waiting1.changeWaitingStatus(WaitingStatus.VISITED);

        // when
        int count = waitingRepository.countByStatusAndToday(WaitingStatus.READY,targetDateTime,date);

        // then
        assertThat(count).isEqualTo(2);
        // 나의 대기 순서 = 3번째
        assertThat(count+1).isEqualTo(3);

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