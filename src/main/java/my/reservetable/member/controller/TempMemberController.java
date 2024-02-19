package my.reservetable.member.controller;

import lombok.RequiredArgsConstructor;
import my.reservetable.member.entity.TempMember;
import my.reservetable.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tempMember")
@RequiredArgsConstructor
public class TempMemberController {

    private final MemberService memberService;


    @PostMapping("/accept")
    public String acceptInvite(Long id){

        memberService.updateTempMemberStatus(id);

        return "";
    }

}
