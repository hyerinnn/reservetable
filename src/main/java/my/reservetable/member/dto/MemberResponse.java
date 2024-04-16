package my.reservetable.member.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private Long id;
    private String email;
    private String nickName;
    private String password;
    private String phoneNumber;
    private MemberRole role;

    @Builder
    private MemberResponse(Long id, String email, String nickName, String password, String phoneNumber, MemberRole role) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static MemberResponse toDto(Member member){
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }
}
