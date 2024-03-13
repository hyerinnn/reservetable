package my.reservetable.owner.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.owner.domain.Owner;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OwnerSignupRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String ownerId;
    private String password;
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickName;
    private String phoneNumber;
    private String email;

    @Builder
    private OwnerSignupRequest(String ownerId, String password, String nickName, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public Owner toEntity() {
        return Owner.builder()
                .ownerId(ownerId)
                .nickName(nickName)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}
