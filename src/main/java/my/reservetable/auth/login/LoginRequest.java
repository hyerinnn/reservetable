package my.reservetable.auth.login;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
