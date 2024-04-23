package my.reservetable.comon.dummy;

import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
import my.reservetable.shop.domain.Address;
import my.reservetable.shop.domain.Shop;
import my.reservetable.shop.domain.ShopCountryCategory;
import my.reservetable.shop.domain.ShopStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalTime;

public class DummyObject {

    protected Member newMember(String email, MemberRole role){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("1234");
        return Member.builder()
                .email(email)
                .password(encodedPassword)
                .nickName("새로운회원")
                .phoneNumber("01012345678")
                .role(role)
                .build();
    }

    protected Member newMockMember(Long id, String email, MemberRole role){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("1234");
        return Member.builder()
                .id(id)
                .email(email)
                .password(encodedPassword)
                .nickName("새로운회원")
                .phoneNumber("01012345678")
                .role(role)
                .build();
    }

    protected Shop createShop(Member ownerMember, String description, ShopCountryCategory countryCategory,
                            ShopStatus status, LocalTime openTime, LocalTime lastOrderTime, String waitingYn){
        return Shop.builder()
                .shopName("뉴레스토랑")
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
