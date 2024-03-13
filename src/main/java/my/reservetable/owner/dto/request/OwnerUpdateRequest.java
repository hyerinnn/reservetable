package my.reservetable.owner.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerUpdateRequest {

    private String ownerId;
    private String password;
    private String nickName;
    private String phoneNumber;
    private String email;

    @Builder
    private OwnerUpdateRequest(String ownerId, String password, String nickName, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}
