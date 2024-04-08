package my.reservetable.owner.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.owner.domain.Owner;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerSignupRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "패스워드는 필수입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickName;

    //@NotBlank(message = "휴대폰 번호는 필수입니다.")
    private String phoneNumber;

    @Builder
    private OwnerSignupRequest(String password, String nickName, String phoneNumber, String email) {
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public Owner toEntity() {
        return Owner.builder()
                .nickName(nickName)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}
