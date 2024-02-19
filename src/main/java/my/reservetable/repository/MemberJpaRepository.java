package my.reservetable.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import my.reservetable.entity.Member;
import org.springframework.stereotype.Repository;

/**
 * 순수 JPA 테스트용도
 * */

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
