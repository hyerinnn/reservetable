package my.reservetable.shop.service;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.dto.request.ShopRegisterRequest;
import my.reservetable.shop.dto.request.ShopUpdateRequest;
import my.reservetable.shop.dto.response.ShopDetailResponse;
import my.reservetable.shop.repository.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@SpringBootTest
//@Transactional
class ShopServiceTest extends IntegrationTestSupport {

    @Autowired ShopService shopService;
    @Autowired ShopRepository shopRepository;
    @Autowired MemberRepository memberRepository;


    @DisplayName("가게목록을 전체 조회한다.")
    @Test
    void getAllShopsWithPageSize() {
        // given
        Member owner = createOwnerMember("owner@owner.com");
        memberRepository.save(owner);

        Shop shop1 = createShop(owner, "맥도날드", ShopCountryCategory.WESTERN,ShopStatus.READY,
                LocalTime.of(8,00),LocalTime.of(22,00),"N");
        Shop shop2 = createShop(owner, "스시나라", ShopCountryCategory.JAPANESE,ShopStatus.OPEN,
                LocalTime.of(11,00),LocalTime.of(23,00),"Y");
        Shop shop3 = createShop(owner, "오늘의백반", ShopCountryCategory.KOREAN,ShopStatus.BREAK_TIME,
                LocalTime.of(9,00),LocalTime.of(20,00),"Y");
        Shop shop4 = createShop(owner, "중화루", ShopCountryCategory.CHINESE,ShopStatus.CLOSE,
                LocalTime.of(10,00),LocalTime.of(00,00),"N");
        shopRepository.saveAll(List.of(shop1,shop2,shop3,shop4));

        //when
        Page<ShopDetailResponse> shops = shopService.getAllShops(PageRequest.of(0,2));

        // then
        assertThat(shops).hasSize(2);
    }

    @DisplayName("가게를 등록한다.")
    @Test
    void registerShopSuccess() {
        // given
        String email = "owner@owner.com";
        Member owner = createOwnerMember(email);
        Member savedMember = memberRepository.save(owner);

        ShopRegisterRequest request = ShopRegisterRequest.builder()
                .shopName("해피식당")
                .memberId(savedMember.getId())
                .shopNumber("02-1234-5678")
                .address(new Address("15151","서울특별시 00로 32"))
                .description("해피식당입니다.")
                .countryCategory(ShopCountryCategory.KOREAN)
                .status(ShopStatus.READY)
                .openTime(LocalTime.of(10,00))
                .lastOrderTime(LocalTime.of(21,00))
                .waitingYn("Y")
                .build();

        //when
        ShopDetailResponse shop = shopService.registerShop(request);

        // then
        assertThat(request.getShopName()).isEqualTo(shop.getShopName());
    }

    @DisplayName("사장정보를 조회할 수 없어서 가게등록에 실패한다.")
    @Test
    void registerShopFail() {
        // given
        ShopRegisterRequest request = ShopRegisterRequest.builder()
                .shopName("해피식당")
                .memberId(1L)
                .shopNumber("02-1234-5678")
                .address(new Address("15151","서울특별시 00로 32"))
                .description("해피식당입니다.")
                .countryCategory(ShopCountryCategory.KOREAN)
                .status(ShopStatus.READY)
                .openTime(LocalTime.of(10,00))
                .lastOrderTime(LocalTime.of(21,00))
                .waitingYn("Y")
                .build();

        // when & then
        assertThatThrownBy(()-> shopService.registerShop(request))
                .isInstanceOf(NotExistMemberException.class)
                .hasMessage("사장님 정보를 찾을 수 없습니다.");
    }

    @DisplayName("가게정보 수정에 성공한다.")
    @Test
    void updateShopSuccess() {
        // given
        String email = "owner@owner.com";
        Member owner = createOwnerMember(email);
        memberRepository.save(owner);


        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN,ShopStatus.READY,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        ShopUpdateRequest request = ShopUpdateRequest.builder()
                .description("설명수정")
                .status(ShopStatus.CLOSE)
                .openTime(LocalTime.of(9,00))
                .lastOrderTime(LocalTime.of(22,00))
                .waitingYn("N")
                .build();
        // when
        ShopDetailResponse modifiedShop =  shopService.updateShop(shop.getShopId(),request);

        // then
        assertThat(modifiedShop.getDescription()).isNotEqualTo("해피식당입니다.");
        assertThat(modifiedShop.getStatus()).isEqualTo(ShopStatus.CLOSE);

    }

    @DisplayName("특정 사장이 등록한 가게를 전체 조회한다.")
    @Test
    void getShopsByOwner() {
        // given
        Member owner = createOwnerMember("owner@owner.com");
        Member saveOwner = memberRepository.save(owner);

        Member owner2 = createOwnerMember( "test@test.com");
        Member saveOwner2 =  memberRepository.save(owner2);

        Shop shop1 = createShop(owner, "맥도날드", ShopCountryCategory.WESTERN,ShopStatus.READY,
                LocalTime.of(8,00),LocalTime.of(22,00),"N");
        Shop shop2 = createShop(owner, "스시나라", ShopCountryCategory.JAPANESE,ShopStatus.OPEN,
                LocalTime.of(11,00),LocalTime.of(23,00),"Y");
        Shop shop3 = createShop(owner, "오늘의백반", ShopCountryCategory.KOREAN,ShopStatus.BREAK_TIME,
                LocalTime.of(9,00),LocalTime.of(20,00),"Y");
        Shop shop4 = createShop(owner2, "중화루", ShopCountryCategory.CHINESE,ShopStatus.CLOSE,
                LocalTime.of(10,00),LocalTime.of(00,00),"N");
        shopRepository.saveAll(List.of(shop1,shop2,shop3,shop4));

        //when
        List<ShopDetailResponse> shops = shopService.getShopsByOwner(saveOwner.getId());

        // then
        assertThat(shops).hasSize(3);
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