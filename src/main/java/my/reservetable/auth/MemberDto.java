package my.reservetable.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {

    private Long id;

    private String email;

    private String password;

    private String nickName;

    private String phoneNumber;

    private String role;
}
