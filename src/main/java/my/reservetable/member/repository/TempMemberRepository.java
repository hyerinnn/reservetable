package my.reservetable.member.repository;

import my.reservetable.member.entity.TempMember;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TempMemberRepository extends JpaRepository<TempMember, Long> {
}
