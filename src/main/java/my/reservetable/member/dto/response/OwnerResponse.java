package my.reservetable.member.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.member.entity.Owner;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OwnerResponse {

    private Long id;
    private String ownerId;
    private String ownerName;
    private String password;
    private String phoneNumber;
    private String email;

    // entity를 dto로 변환
    public static OwnerResponse toDto(Owner owner){
        return OwnerResponse.builder()
                .id(owner.getId())
                .ownerId(owner.getOwnerId())
                .ownerName(owner.getOwnerName())
                .password(owner.getPassword())
                .phoneNumber(owner.getPhoneNumber())
                .email(owner.getEmail())
                .build();
    }
}
