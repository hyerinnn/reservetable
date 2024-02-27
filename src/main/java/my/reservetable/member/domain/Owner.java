package my.reservetable.member.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String ownerName;

    private String password;

    //@Column(nullable = false, length = 12)
    private String phoneNumber;

    //@Column(nullable = false)
    private String email;

    //TODO: 생성일/생성자/수정일/수정자 작업하기 -> @Embedded  or  Auditing 클래스 상속?

    @Builder
    public Owner(String ownerId, String ownerName, String password, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void update(String ownerId, String ownerName, String password, String phoneNumber, String email){
        if(!StringUtils.isEmpty(ownerId))
            this.ownerId = ownerId;
        if(!StringUtils.isEmpty(ownerName))
            this.ownerName = ownerName;
        if(!StringUtils.isEmpty(password))
            this.password = password;
        if(!StringUtils.isEmpty(phoneNumber))
            this.phoneNumber = phoneNumber;
        if(!StringUtils.isEmpty(email))
            this.email = email;
    }
}
