package my.reservetable.point.repository;

import my.reservetable.member.domain.Member;
import my.reservetable.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    Optional<Point> findByMember(Member member);
}
