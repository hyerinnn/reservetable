package my.reservetable.tmp;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.reservetable.member.entity.Member;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(name = "shop_name")
    private String name;

    @ManyToOne(optional = false)  // 연관관계 주인
    @JoinColumn(name="owner_id")
    private Member member;


    // TODO : 그룹에 속한 임시회원

}
