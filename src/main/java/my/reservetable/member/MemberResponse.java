package my.reservetable.member;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.member.domain.Member;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private Long id;
    private String email;
    private String nickName;
    private String password;
    private String phoneNumber;

    @Builder
    private MemberResponse(Long id, String email, String nickName, String password, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public static MemberResponse toDto(Member member){
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
