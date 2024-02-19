package my.reservetable.member.repository;

import my.reservetable.member.entity.Member;
import my.reservetable.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("aaaa", "aaa@abc.com", "01011112222");

        Member newMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(newMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
    }

}