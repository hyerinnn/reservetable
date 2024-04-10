package my.reservetable.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.member.MemberUpdateRequest;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.member.MemberRepository;
import my.reservetable.member.MemberResponse;
import my.reservetable.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(member -> MemberResponse.toDto(member))
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));
    }

    public MemberResponse getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(member -> MemberResponse.toDto(member))
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));
    }

    @Transactional
    public MemberResponse updateMember(MemberUpdateRequest request){
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));

        member.update(
                request.getNickName(),
                request.getPhoneNumber()
        );
        return MemberResponse.toDto(member);
    }

}
