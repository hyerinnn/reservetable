package my.reservetable.waiting.service;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.exception.NoRegisterWaitingException;
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
import my.reservetable.waiting.dto.request.WaitingRegisterRequest;
import my.reservetable.waiting.dto.response.WaitingResponse;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@SpringBootTest
//@Transactional
class WaitingByUserServiceTest extends IntegrationTestSupport {

    @Autowired WaitingByUserService waitingByUserService;
    @Autowired WaitingService waitingService;
    @Autowired WaitingRepository waitingRepository;
    @Autowired ShopRepository shopRepository;
    @Autowired MemberRepository memberRepository;

    Member owner;
    Member user1, user2, user3, user4;
    Shop shop;

    @BeforeEach
    void setUp(){
        owner = createMember("ownerTest@ownerTest.com", MemberRole.OWNER);
        memberRepository.save(owner);

        shop = createShop(owner, "테스트식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.OPEN,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        user1 = createMember("user1@user.com", MemberRole.USER);
        user2 = createMember("user2@user.com", MemberRole.USER);
        user3 = createMember("user3@user.com", MemberRole.USER);
        user4 = createMember("user4@user.com", MemberRole.USER);
        memberRepository.saveAll(List.of(owner, user1, user2, user3, user4));
    }

    @DisplayName("웨이팅을 성공적으로 생성한다")
    @Test
    void registerWaiting() {
        // given
        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime registeredDateTime2 = LocalDateTime.of(date, LocalTime.of(17, 30, 4));
        LocalDateTime registeredDateTime3 = LocalDateTime.of(date, LocalTime.of(17, 33, 48));
        LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));

        Waiting waiting1 = Waiting.create(shop, user1, 2, 1, registeredDateTime1);
        Waiting waiting2 = Waiting.create(shop, user2, 3, 2, registeredDateTime2);
        Waiting waiting3 = Waiting.create(shop, user3, 4, 3, registeredDateTime3);
        waitingRepository.saveAll(List.of(waiting1, waiting2, waiting3));
        waiting1.changeWaitingStatus(WaitingStatus.VISITED);

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .memberId(user4.getId())
                .shopId(shop.getShopId())
                .headCount(5)
                .registeredDateTime(targetDateTime)
                .build();

        // when
        WaitingResponse waitingResponse = waitingByUserService.registerWaiting(request);

        // then
        assertThat(waitingResponse.getMyWaitingOrder()).isEqualTo(3);
        //assertThat(waitingResponse.getWaitingNumber()).isEqualTo(4);
    }

    @DisplayName("가게의 status가 open이 아닌 경우, 웨이팅 등록에 실패한다.")
    @Test
    void registerWaitingFail() {
        // given
        shop.changeStatus(ShopStatus.BREAK_TIME);

        LocalDate date = LocalDate.now();
        LocalDateTime newWaitingTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .memberId(user1.getId())
                .shopId(shop.getShopId())
                .headCount(5)
                .registeredDateTime(newWaitingTime)
                .build();

        // when & then
        assertThatThrownBy(() -> waitingByUserService.registerWaiting(request))
                .isInstanceOf(NoRegisterWaitingException.class)
                .hasMessage("가게가 운영중이지 않아서 웨이팅을 할 수 없습니다.");
    }

    @DisplayName("이미 웨이팅중인 회원은 같은 가게에 웨이팅 등록에 실패한다.")
    @Test
    void registerDuplicatedWaitingFail() {
        // given
        LocalDate date = LocalDate.now();
        LocalDateTime alreadyWaitingTime = LocalDateTime.of(date, LocalTime.of(17, 30, 2));
        LocalDateTime newWaitingTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));

        Waiting alreadyWaiting = Waiting.create(shop, user1, 2, 1, alreadyWaitingTime);
        waitingRepository.save(alreadyWaiting);

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .memberId(user1.getId())
                .shopId(shop.getShopId())
                .headCount(5)
                .registeredDateTime(newWaitingTime)
                .build();

        // when & then
        assertThatThrownBy(() -> waitingByUserService.registerWaiting(request))
                .isInstanceOf(NoRegisterWaitingException.class)
                .hasMessage("이미 웨이팅한 회원입니다.");
    }

    @DisplayName("나의 현재 웨이팅을 조회한다.")
    @Test
    void getMyWaiting() {
        // given
        LocalDate date = LocalDate.now();
        LocalDateTime targetDateTime = LocalDateTime.of(date, LocalTime.of(17, 40, 13));

        WaitingRegisterRequest request = WaitingRegisterRequest.builder()
                .memberId(user1.getId())
                .shopId(shop.getShopId())
                .headCount(5)
                .registeredDateTime(targetDateTime)
                .build();

        WaitingResponse waitingResponse = waitingByUserService.registerWaiting(request);

        // when
        WaitingResponse myWaitingResponse = waitingByUserService.getMyNowWaiting(waitingResponse.getUserId(),waitingResponse.getShopId());

        // then
        assertThat(myWaitingResponse.getWaitingId()).isEqualTo(waitingResponse.getWaitingId());
    }

    @DisplayName("나의 모든 웨이팅을 조회한다.")
    @Test
    void getMyWaitings() {
        // given
        LocalDate date = LocalDate.now();
        LocalDateTime registeredDateTime1 = LocalDateTime.of(date, LocalTime.of(17, 40, 13));
        Waiting waiting = Waiting.create(shop, user1,2, 1, registeredDateTime1);
        waitingRepository.save(waiting);
        waitingService.changeWaitingStatus(waiting.getWaitingId(),WaitingStatus.VISITED);

        Waiting waiting2 = Waiting.create(shop, user1,3, 1, registeredDateTime1);
        waitingRepository.save(waiting2);

        // when
        List<WaitingResponse> myWaitingResponse = waitingByUserService.getAllMyWaitings(user1.getId());

        // then
        assertThat(myWaitingResponse).hasSize(2)
                .extracting("headCount")
                .contains(2,3);
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