package my.reservetable.like;

import my.reservetable.IntegrationTestSupport;
import my.reservetable.like.dto.LikeRequest;
import my.reservetable.like.dto.LikesWithCountResponse;
import my.reservetable.like.repository.LikesRepository;
import my.reservetable.like.service.LikeService;
import my.reservetable.member.domain.Member;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import my.reservetable.shop.repository.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LikeServiceTest extends IntegrationTestSupport {

    @Autowired ShopRepository shopRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired LikeService likeService;
    @Autowired LikesRepository likesRepository;

    @DisplayName("좋아요를 누른적이 없으면 좋아요를 누를 수 있다.")
    @Test
    void likeShop() {
        // given
        Member owner = createMember("owner@owner.com");
        Member user = createMember("user@user.com");
        memberRepository.saveAll(List.of(owner, user));

        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        LikeRequest request = LikeRequest.builder()
                .memberId(user.getId())
                .shopId(shop.getShopId())
                .build();

        // when
        int count = likeService.likeShop(request);

        // then
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("이미 좋아요를 누른적이 있다면, 좋아요를 취소한다")
    @Test
    void cancelLikeShop() {
        // given
        Member owner = createMember("owner@owner.com");
        Member user = createMember("user@user.com");
        memberRepository.saveAll(List.of(owner, user));

        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        shopRepository.save(shop);

        LikeRequest request = LikeRequest.builder()
                .memberId(user.getId())
                .shopId(shop.getShopId())
                .build();

        // when
        likeService.likeShop(request);
        likeService.likeShop(request);

        int count = likesRepository.countByMemberAndShop(user, shop);

        // then
        assertThat(count).isEqualTo(0);
    }

    @DisplayName("나의 좋아요 목록을 조회한다.")
    @Test
    void getLikesByMember() {
        // given
        Member owner = createMember("owner@owner.com");
        Member user = createMember("user@user.com");
        memberRepository.saveAll(List.of(owner, user));

        // when
        Shop shop = createShop(owner, "해피식당입니다.", ShopCountryCategory.KOREAN, ShopStatus.OPEN,
                LocalTime.of(10,00),LocalTime.of(21,00),"Y");
        Shop shop2 = createShop(owner, "스시나라", ShopCountryCategory.JAPANESE,ShopStatus.OPEN,
                LocalTime.of(11,00),LocalTime.of(23,00),"Y");
        shopRepository.saveAll(List.of(shop,shop2));

        LikeRequest request = LikeRequest.builder()
                .memberId(user.getId())
                .shopId(shop.getShopId())
                .build();

        LikeRequest request2 = LikeRequest.builder()
                .memberId(user.getId())
                .shopId(shop2.getShopId())
                .build();

        likeService.likeShop(request);
        likeService.likeShop(request2);

        LikesWithCountResponse likes = likeService.getLikesByMember(user.getId());

        // then
        assertThat(likes.getLikes()).hasSize(2);
        assertThat(likes.getCount()).isEqualTo(2);
    }

    private Member createMember(String email){
        return Member.builder()
                .nickName("사장님A")
                .password("1111")
                .email(email)
                .phoneNumber("01027374848")
                .build();
    }

    private Shop createShop(Member owner, String description, ShopCountryCategory countryCategory,
                            ShopStatus status, LocalTime openTime, LocalTime lastOrderTime, String waitingYn){
        return Shop.builder()
                .shopName("해피식당")
                .member(owner)
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