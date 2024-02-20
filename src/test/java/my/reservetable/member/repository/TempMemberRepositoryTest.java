package my.reservetable.member.repository;

import my.reservetable.member.entity.MemberStatus;
import my.reservetable.member.entity.TempMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class TempMemberRepositoryTest {

    @Autowired
    TempMemberRepository tempMemberRepository;

    @Test
    public void testTempMember(){

        TempMember tempMember = new TempMember(1L,"임시회원1","01022223333","tmp001@abc.com", MemberStatus.READY.toString(), "agdfg");
        TempMember tempMember2 = new TempMember(3L,"임시회원2","01022223333","tmp002@abc.com", MemberStatus.READY.toString(), "gdsage");

        TempMember newTempMember = tempMemberRepository.save(tempMember);
        TempMember newTempMember2 = tempMemberRepository.save(tempMember2);


        TempMember findTempMember = tempMemberRepository.findById(newTempMember.getId()).get();
        TempMember findTempMember2 = tempMemberRepository.findById(newTempMember2.getId()).get();

        assertThat(findTempMember.getId()).isEqualTo(tempMember.getId());
        assertThat(findTempMember2.getId()).isEqualTo(tempMember2.getId());

    }

}