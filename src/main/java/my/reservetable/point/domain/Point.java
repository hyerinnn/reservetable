package my.reservetable.point.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.reservetable.config.AuditingEntity;
import my.reservetable.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    private Point(Member member, int point) {
        this.member = member;
        this.point = point;
    }

    public void addPoint(int point){
        this.point += point;
    }

    public void subtractPoint(int point){
        if(checkPointAmount(point)){
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        this.point -= point;
    }

    public boolean checkPointAmount(int point){
       return this.point > point;
    }
}
