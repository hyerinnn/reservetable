package my.reservetable.member.service;

import my.reservetable.member.dto.TempMemberRequest;
import my.reservetable.member.entity.MemberStatus;
import my.reservetable.member.entity.TempMember;
import my.reservetable.member.repository.TempMemberRepository;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("testInviteTempMember(): 초대 시, 임시회원을 등록한다.")
    public void testInviteTempMember(){

        //TempMember tempMember = new TempMember(1L,"임시회원1","01022223333","tmp001@abc.com", MemberStatus.READY.toString());
        //TempMember tempMember2 = new TempMember(3L,"임시회원2","01022223333","tmp002@abc.com", MemberStatus.READY.toString());

        TempMemberRequest tempMember1 = new TempMemberRequest(1L,"임시회원1","01022223333","tmp001@abc.com", MemberStatus.READY.toString());
        TempMemberRequest tempMember2 = new TempMemberRequest(3L,"임시회원2","01022223333","tmp002@abc.com", MemberStatus.READY.toString());


        //TODO : inviteResponse 로 변경 필요
        TempMember newTempMember1 = memberService.inviteTempMember(tempMember1);
        TempMember newTempMember2 = memberService.inviteTempMember(tempMember2);

        //TempMember findTempMember = tempMemberRepository.findById(newTempMember).get();
        TempMember findTempMember1 = tempMemberRepository.findById(newTempMember1.getId()).get();
        TempMember findTempMember2 = tempMemberRepository.findById(newTempMember2.getId()).get();

        //assertThat(findTempMember.getId()).isEqualTo(tempMember1.getId());
        //assertThat(findTempMember2.getId()).isEqualTo(tempMember2.getId());

        assertThat(findTempMember1.getId()).isEqualTo(newTempMember1.getId());

    }

    @Test
    @DisplayName("updateTempMemberStatus(): 초대수락 시, 임시회원 상태 활성화")
    public void updateTempMemberStatus(){
        TempMemberRequest tempMember = new TempMemberRequest(1L,"임시회원1","01022223333","tmp001@abc.com", MemberStatus.READY.toString());
        TempMember newTempMember = memberService.inviteTempMember(tempMember);

        memberService.updateTempMemberStatus(newTempMember.getId());

        assertThat(newTempMember.getStatus()).isEqualTo("ACCEPT");
    }


}