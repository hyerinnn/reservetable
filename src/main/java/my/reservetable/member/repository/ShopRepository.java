package my.reservetable.member.repository;

import my.reservetable.member.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShopRepository extends JpaRepository<Shop, Long> {
}
