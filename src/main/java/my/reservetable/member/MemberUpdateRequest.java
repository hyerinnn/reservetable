package my.reservetable.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateRequest {

    private Long id;
    private String email;
    private String nickName;
    private String phoneNumber;


    @Builder
    private MemberUpdateRequest(Long id, String nickName, String phoneNumber, String email) {
        this.id = id;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}
