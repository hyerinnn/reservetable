package my.reservetable.waiting.repository;

import my.reservetable.comon.dummy.DummyObject;
import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import my.reservetable.waiting.domain.Waiting;
import my.reservetable.waiting.domain.WaitingStatus;
import org.junit.jupiter.api.BeforeEach;
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
class WaitingRepositoryTest extends DummyObject {

    @Autowired WaitingRepository waitingRepository;
    @Autowired ShopRepository shopRepository;
    @Autowired MemberRepository memberRepository;

    Member owner;
    Member user1, user2, user3, user4;
    Shop shop;
    Waiting waiting1, waiting2, waiting3, waiting4, waiting5, targetWaiting;
    LocalDate date;
    LocalDateTime targetDateTime;

    @BeforeEach
    void setUp(){
        owner = newMember("owner@owner.com", MemberRole.OWNER);
        memberRepository.save(owner);

        shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        user1 = newMember("user1@user.com", MemberRole.USER);
        user2 = newMember("user2@user.com", MemberRole.USER);
        user3 = newMember("user3@user.com", MemberRole.USER);
        user4 = newMember("user4@user.com", MemberRole.USER);
        memberRepository.saveAll(List.of(owner, user1, user2, user3, user4));

        date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(17, 30, 4));
        LocalDateTime registeredDateTime3 = LocalDateTime.of(date, LocalTime.of(17, 33, 48));
        targetDateTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
        LocalDateTime registeredDateTime4 = LocalDateTime.of(date, LocalTime.of(17, 55, 00));
        LocalDateTime registeredDateTime5 = LocalDateTime.of(date, LocalTime.of(18, 10, 00));

        waiting1 = Waiting.create(shop,user1,2, 1, registeredDateTime1);
        waiting2 = Waiting.create(shop,user2,3, 2, registeredDateTime2);
        waiting3 = Waiting.create(shop,user3,4, 3, registeredDateTime3);
        targetWaiting = Waiting.create(shop,user4,3, 4, targetDateTime);
        waiting4 = Waiting.create(shop,user4,5, 5, registeredDateTime4);
        waiting5 = Waiting.create(shop,user4,2, 6, registeredDateTime5);

        waitingRepository.saveAll(List.of(waiting1,waiting2,waiting3,waiting4,waiting5,targetWaiting));
    }

    @DisplayName("[웨이팅 번호] 타겟 웨이팅생성시간보다 먼저 생성된 웨이팅 수를 조회한다.")
    @Test
    void getCountBeforeTargetWaiting() {
        // given
        // when
        int count = waitingRepository.getRegisteredDateTimeBefore(targetDateTime, date);

        // then
        assertThat(count).isEqualTo(3);
    }

    @DisplayName("[현재 나의 순서] 타겟 웨이팅생성시간보다 먼저 생성된 웨이팅 중, '입장대기'인 웨이팅 수를 조회한다.")
    @Test
    void getCountBeforeAndReadyTargetWaiting() {
        // given
        waiting1.changeWaitingStatus(WaitingStatus.VISITED);

        // when
        int count = waitingRepository.countByStatusAndToday(WaitingStatus.READY,targetDateTime,date);

        // then
        assertThat(count).isEqualTo(2);
        // 나의 대기 순서 = 3번째
        assertThat(count+1).isEqualTo(3);
    }

}