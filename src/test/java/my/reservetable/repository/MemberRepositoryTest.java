package my.reservetable.repository;

import my.reservetable.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("aaaa", "aaa@abc.com");

        Member newMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(newMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
    }

}