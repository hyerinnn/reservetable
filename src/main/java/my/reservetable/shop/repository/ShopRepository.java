package my.reservetable.shop.repository;

import my.reservetable.member.domain.Member;
import my.reservetable.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    List<Shop> findByMember(Member member);

}
