package my.reservetable.owner.domain;

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
public class Owner extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Id
    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String nickName;

    private String password;

    //@Column(nullable = false, length = 12)
    private String phoneNumber;

    //@Column(nullable = false)
    private String email;

    //TODO : 다대일 양방향 매핑
/*
    @OneToMany(mappedBy = "owner")
    private List<Shop> shops = new ArrayList<>();
*/

    @Builder
    private Owner(String ownerId, String nickName, String password, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void update(String ownerId, String nickName, String password, String phoneNumber, String email){
        if(!StringUtils.isEmpty(ownerId))
            this.ownerId = ownerId;
        if(!StringUtils.isEmpty(nickName))
            this.nickName = nickName;
        if(!StringUtils.isEmpty(password))
            this.password = password;
        if(!StringUtils.isEmpty(phoneNumber))
            this.phoneNumber = phoneNumber;
        if(!StringUtils.isEmpty(email))
            this.email = email;
    }
}
