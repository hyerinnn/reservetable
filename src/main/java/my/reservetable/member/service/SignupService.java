package my.reservetable.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.reservetable.exception.DuplicateMemberException;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.member.dto.MemberResponse;
import my.reservetable.member.dto.MemberUpdateRequest;
import my.reservetable.member.domain.Member;
import my.reservetable.member.domain.MemberRole;
import my.reservetable.member.dto.SignupRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignupService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse signupOwner(SignupRequest request){
        validateDuplicateMember(request.getEmail());
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Member newMember =  memberRepository.save(request.toEntity(encryptedPassword, MemberRole.OWNER));
        return MemberResponse.toDto(newMember);
    }

    @Transactional
    public MemberResponse signupUser(SignupRequest request){
        validateDuplicateMember(request.getEmail());
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Member newMember =  memberRepository.save(request.toEntity(encryptedPassword, MemberRole.USER));
        return MemberResponse.toDto(newMember);
    }

    private void validateDuplicateMember(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isPresent()) {
            throw new DuplicateMemberException("이미 가입된 회원입니다.");
        }
    }

    @Transactional
    public MemberResponse updatePassword(MemberUpdateRequest request){
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(() ->  new NotExistMemberException("회원을 찾을 수 없습니다."));

/*        member.update(
                request.getNickName(),
                request.getPhoneNumber()
        );*/
        return MemberResponse.toDto(member);
    }

}