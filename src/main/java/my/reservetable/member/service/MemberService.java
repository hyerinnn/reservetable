package my.reservetable.member.service;

import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.InviteResponse;
import my.reservetable.member.dto.TempMemberRequest;
import my.reservetable.member.entity.MemberStatus;
import my.reservetable.member.entity.TempMember;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.member.repository.TempMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

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
    public InviteResponse inviteTempMember(TempMemberRequest request){

        // TODO : 필수값 체크, 밸리데이션  분리

        /*
        // 예외처리 테스트
        Optional<TempMember> findMember = tempMemberRepository.findByPhoneNumber(request.getPhoneNumber());

            if(findMember.isPresent()){
                throw new IllegalStateException("이미 초대완료된 회원입니다.");
            }

       */
        //request.setStatus(MemberStatus.READY.toString());


        // 랜덤 url 생성
        String randomUrl = UUID.randomUUID().toString();

        TempMember tempMember = tempMemberRepository.save(request.toEntity());

        return InviteResponse.builder()
                .tempMemberId(tempMember.getId())
                .urlId(randomUrl)
                .build();
    }

    /**
     * 임시회원 수락 -> 상태 활성화
     * */
    public Long updateTempMemberStatus(Long id, String randomUrl){

        TempMember member = tempMemberRepository.getReferenceById(id);
        //TempMember member = tempMemberRepository.findByIdAndTmpUrl(id,randomUrl).get();

        // TODO : 이미 수락한 초대 예외처리

        member.setStatus(MemberStatus.ACCEPT.toString());
        // 세터 쓰지말고 빌더쓰기.

        tempMemberRepository.save(member);
        return member.getId();
    }



}
