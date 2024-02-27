package my.reservetable.member.repository;

import my.reservetable.member.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByOwnerId(String ownerId);
}
