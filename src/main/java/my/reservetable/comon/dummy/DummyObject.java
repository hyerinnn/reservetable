package my.reservetable.comon.dummy;

import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
}
