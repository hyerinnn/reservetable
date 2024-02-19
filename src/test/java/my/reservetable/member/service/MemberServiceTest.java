package my.reservetable.member.service;

import my.reservetable.member.entity.MemberStatus;
import my.reservetable.member.entity.TempMember;
import my.reservetable.member.repository.TempMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    TempMemberRepository tempMemberRepository;

    @Test
    public void testInviteTempMember(){

        TempMember tempMember = new TempMember(1L,"임시회원1","01022223333","tmp001@abc.com", MemberStatus.READY.toString());
        TempMember tempMember2 = new TempMember(3L,"임시회원2","01022223333","tmp002@abc.com", MemberStatus.READY.toString());

        Long newTempMemberId = memberService.inviteTempMember(tempMember);
        Long newTempMember2Id = memberService.inviteTempMember(tempMember2);

        TempMember findTempMember = tempMemberRepository.findById(newTempMemberId).get();
        TempMember findTempMember2 = tempMemberRepository.findById(newTempMember2Id).get();

        assertThat(findTempMember.getId()).isEqualTo(tempMember.getId());
        assertThat(findTempMember2.getId()).isEqualTo(tempMember2.getId());

    }

    @Test
    public void updateTempMemberStatus(){
        TempMember tempMember = new TempMember(1L,"임시회원1","01022223333","tmp001@abc.com", MemberStatus.READY.toString());
        Long newTempMemberId = memberService.inviteTempMember(tempMember);

        memberService.updateTempMemberStatus(newTempMemberId);

        assertThat(tempMember.getStatus()).isEqualTo("ACCEPT");
    }

}