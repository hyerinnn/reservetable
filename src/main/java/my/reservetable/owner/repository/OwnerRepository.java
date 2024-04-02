package my.reservetable.owner.repository;

import my.reservetable.owner.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {

//TODO : Owner와 Shop 양방향 관계설정 시 사용예정
/*
    @Query(value = "select o from Owner o join fetch o.shops  where o.ownerId = :ownerId")
    Owner getOwnerAndShops(@Param("ownerId") String ownerId);

*/

}
