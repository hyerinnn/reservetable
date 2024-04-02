package my.reservetable.shop.repository;

import my.reservetable.owner.domain.Owner;
import my.reservetable.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long> {

    List<Shop> findByOwner(Owner owner);

}
