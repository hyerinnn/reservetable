package my.reservetable.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temp_member_id")
    private Long id;

    // 초대자 id
    @Column(nullable = false)
    private Long inviteId;

/*

    @Column(nullable = false)
    @JoinColumn(name="invite_id")
    private Member member;

*/

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, length = 12)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    //@ColumnDefault(MemberStatus.READY)
    private String status;

    @Column
    private String tmpUrl;

/*
    @Column(nullable = false)
    private String group;
*/

    @Builder
    public TempMember(Long inviteId, String userName, String phoneNumber, String email, String status, String tmpUrl) {
        this.inviteId = inviteId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status=status;
        this.tmpUrl=tmpUrl;
    }

}
