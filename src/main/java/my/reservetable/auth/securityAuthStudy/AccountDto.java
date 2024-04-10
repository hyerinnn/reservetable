package my.reservetable.auth.securityAuthStudy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

    private Long id;

    private String email;

    private String password;

    private String nickName;

    private String phoneNumber;

    private String roles;
}
