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
    @Column(nullable = false)
    private Long ownerId;

    //@Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickName;

    private String password;

    //@Column(nullable = false, length = 12)
    private String phoneNumber;

    //TODO : Owner와 Shop 양방향 관계설정 시 사용예정
/*
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    //@JoinColumn(name="shop")
    private List<Shop> shops = new ArrayList<>();
*/

    @Builder
    private Owner(Long ownerId, String nickName, String password, String phoneNumber, String email) {
        this.ownerId = ownerId;
        this.nickName = nickName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void update(String nickName, String password, String phoneNumber, String email){
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
