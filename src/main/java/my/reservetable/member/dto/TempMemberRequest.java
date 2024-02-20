package my.reservetable.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.member.entity.MemberStatus;
import my.reservetable.member.entity.TempMember;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TempMemberRequest {

    private Long inviteId;
    private String userName;
    private String phoneNumber;
    private String email;
    private String status;

/*
    // @AllArgsConstructor 애노테이션있으니까 불필요
    public TempMemberRequest(Long inviteId, String userName, String phoneNumber, String email, String status) {
        this.inviteId = inviteId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
    }
*/

    // Dto를 엔티티로 만들어주는 부분
    public TempMember toEntity(){
        return TempMember.builder()
                .inviteId(inviteId)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .email(email)
                //.status(status)
                .status(MemberStatus.READY.toString())
                .build();

    }



}
