package my.reservetable.member.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.config.AuditingEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 12)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    private Member(String email, String nickName, String password, String phoneNumber, MemberRole role) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void update(String nickName, String phoneNumber){
        if(!StringUtils.isEmpty(nickName))
            this.nickName = nickName;
        if(!StringUtils.isEmpty(phoneNumber))
            this.phoneNumber = phoneNumber;
    }

}
