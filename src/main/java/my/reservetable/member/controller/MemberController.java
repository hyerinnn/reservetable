package my.reservetable.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.MemberResponse;
import my.reservetable.member.dto.MemberUpdateRequest;
import my.reservetable.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "Member", description = "Member API")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable Long id){
        return memberService.getMemberById(id);
    }

    @PutMapping("/{id}")
    public MemberResponse update(@Valid @RequestBody MemberUpdateRequest request,
                                     @PathVariable Long id){
        return memberService.update(request, id);
    }

}
