package my.reservetable.shop.domain;


import jakarta.persistence.*;
import lombok.*;
import my.reservetable.member.domain.Owner;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(nullable = false, name = "shop_name")
    private String name;

    @ManyToOne(optional = false)  // 연관관계 주인
    @JoinColumn(name="owner_id")
    private Owner owner;

    @Builder
    public Shop(String name, Owner owner) {
        this.name = name;
        this.owner = owner;
    }

}
