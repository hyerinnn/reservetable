package my.reservetable.like.repository;

import my.reservetable.like.domain.Likes;
import my.reservetable.member.domain.Member;
import my.reservetable.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    // ==== like ==== //
    Optional<Likes> findByMemberAndShop(Member member, Shop shop);
    void deleteByMemberAndShop(Member member, Shop shop);
    int countByMemberAndShop(Member member, Shop shop);

    // ==== shop ==== //
    int countByShop(Shop shop);

    // ==== member ==== //
    List<Likes> findAllByMember(Member member);
    int countByMember(Member member);
}
