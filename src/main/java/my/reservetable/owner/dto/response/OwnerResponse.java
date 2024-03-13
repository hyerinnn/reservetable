package my.reservetable.owner.dto.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.owner.domain.Owner;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OwnerResponse {

    private String ownerId;
    private String nickName;
    private String password;
    private String phoneNumber;
    private String email;

    @Builder
    private OwnerResponse(String ownerId, String nickName, String password, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // entity를 dto로 변환
    public static OwnerResponse toDto(Owner owner){
        return OwnerResponse.builder()
                .ownerId(owner.getOwnerId())
                .nickName(owner.getNickName())
                .password(owner.getPassword())
                .phoneNumber(owner.getPhoneNumber())
                .email(owner.getEmail())
                .build();
    }
}
