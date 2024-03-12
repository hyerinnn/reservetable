package my.reservetable.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import my.reservetable.member.domain.Owner;


@NoArgsConstructor
@Getter
public class OwnerSignupRequest {

    private String ownerId;
    private String password;
    private String ownerName;
    private String phoneNumber;
    private String email;

    @Builder
    public OwnerSignupRequest(String ownerId, String password, String ownerName, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.password = password;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public Owner toEntity() {
        return Owner.builder()
                .ownerId(ownerId)
                .ownerName(ownerName)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}
