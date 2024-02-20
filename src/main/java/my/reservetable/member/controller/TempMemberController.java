package my.reservetable.member.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.InviteResponse;
import my.reservetable.member.dto.TempMemberRequest;
import my.reservetable.member.entity.TempMember;
import my.reservetable.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tempMember")
@RequiredArgsConstructor
public class TempMemberController {

    private final MemberService memberService;


    @PostMapping("/invite")
    public ResponseEntity<InviteResponse> inviteTempMember(@RequestBody TempMemberRequest request){
        InviteResponse inviteMember = memberService.inviteTempMember(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inviteMember);
    }



    @PostMapping("/accept/{randomUrl}/{id}")
    public ResponseEntity<Long> acceptInvite(@PathVariable(name = "id") Long id,
                                              @PathVariable(name = "randomUrl") String randomUrl  ){
        Long tempMemberId = memberService.updateTempMemberStatus(id, randomUrl);

        return ResponseEntity.ok().body(tempMemberId);
    }

}
