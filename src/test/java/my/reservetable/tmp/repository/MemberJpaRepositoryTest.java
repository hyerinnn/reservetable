package my.reservetable.tmp.repository;

import my.reservetable.tmp.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember(){
/*
        Member member = new Member("aaaa", "aaa@abc.com","01011112222");

        Member newMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(newMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
 */
    }

}