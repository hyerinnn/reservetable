package my.reservetable.member.repository;

import my.reservetable.member.entity.TempMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TempMemberRepository extends JpaRepository<TempMember, Long> {
    Optional<TempMember> findByPhoneNumber(String phoneNumber);
}
