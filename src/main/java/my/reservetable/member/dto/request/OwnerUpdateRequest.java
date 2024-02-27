package my.reservetable.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OwnerUpdateRequest {

    private String ownerId;
    private String password;
    private String ownerName;
    private String phoneNumber;
    private String email;

    @Builder
    public OwnerUpdateRequest(String ownerId, String password, String ownerName, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.password = password;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
/*
    // 불필요
    public Owner toEntity() {
        return Owner.builder()
                .ownerId(ownerId)
                .ownerName(ownerName)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
 */
}
