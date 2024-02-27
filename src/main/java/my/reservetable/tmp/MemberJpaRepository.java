package my.reservetable.tmp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import my.reservetable.member.domain.Owner;
import org.springframework.stereotype.Repository;

/**
 * 순수 JPA 테스트용도
 * */

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Owner save(Owner owner){
        em.persist(owner);
        return owner;
    }

    public Owner find(Long id){
        return em.find(Owner.class, id);
    }
}
