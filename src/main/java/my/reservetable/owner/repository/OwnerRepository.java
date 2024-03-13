package my.reservetable.owner.repository;

import my.reservetable.owner.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByOwnerId(String ownerId);
}
