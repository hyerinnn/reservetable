package my.reservetable.member.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.TempMemberDto;
import my.reservetable.member.entity.MemberStatus;
import my.reservetable.member.entity.TempMember;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.member.repository.TempMemberRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TempMemberRepository tempMemberRepository;


    /**
     * 임시회원 초대(등록)
     * */
    @Transactional
    public Long inviteTempMember(TempMember member){

        // TODO : TempMember -> TempMemberDto로 바꾸기


        // TODO : 필수값 체크, 밸리데이션  분리

        Optional<TempMember> findMember = tempMemberRepository.findByPhoneNumber(member.getPhoneNumber());

        // 예외처리 테스트
        /*
            if(findMember.isPresent()){
                throw new IllegalStateException("이미 초대완료된 회원입니다.");
            }
       */
        member.setStatus(MemberStatus.READY.toString());
        tempMemberRepository.save(member);

        return member.getId();
    }

    /**
     * 임시회원 수락 -> 상태 활성화
     * */
    public void updateTempMemberStatus(Long id){

        TempMember member = tempMemberRepository.getReferenceById(id);
        member.setStatus(MemberStatus.ACCEPT.toString());

        tempMemberRepository.save(member);
    }



}
