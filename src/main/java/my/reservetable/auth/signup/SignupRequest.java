package my.reservetable.auth.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.member.Member;
import my.reservetable.member.MemberRole;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "패스워드는 필수입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickName;

    //@NotBlank(message = "휴대폰 번호는 필수입니다.")
    private String phoneNumber;

    private MemberRole role;

    @Builder
    private SignupRequest(String email, String password, String nickName, String phoneNumber, MemberRole role) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


    public Member toEntity(String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }
}
