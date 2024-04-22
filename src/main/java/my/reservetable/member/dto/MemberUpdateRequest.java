package my.reservetable.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateRequest {

    private String nickName;
    private String phoneNumber;


    @Builder
    private MemberUpdateRequest(String nickName, String phoneNumber) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }

}
