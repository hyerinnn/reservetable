package my.reservetable.member.controller;

import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.TempMemberDto;
import my.reservetable.member.entity.TempMember;
import my.reservetable.member.repository.MemberRepository;
import my.reservetable.member.repository.TempMemberRepository;
import my.reservetable.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/invite")
    public Long inviteTempMember(TempMember member){
        return memberService.inviteTempMember(member);
    }

}
