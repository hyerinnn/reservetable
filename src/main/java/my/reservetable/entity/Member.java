package my.reservetable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
//@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;


    public Member(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public Member() {

    }
}
