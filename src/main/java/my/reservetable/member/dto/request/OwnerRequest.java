package my.reservetable.member.dto.request;

import lombok.*;
import my.reservetable.member.entity.Owner;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OwnerRequest {

    private String ownerId;
    private String password;
    private String ownerName;
    private String phoneNumber;
    private String email;

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
