package my.reservetable.member.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "특정 회원 조회", description = "특정 회원의 아이디로 회원정보를 조회한다.")
    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable Long id){
        return memberService.getMemberById(id);
    }

    @Operation(summary = "특정 회원정보 수정", description = "특정 회원의 정보를 수정한다.")

    @PutMapping("/{id}")
    public MemberResponse update(@Valid @RequestBody MemberUpdateRequest request,
                                     @PathVariable Long id){
        return memberService.update(request, id);
    }

}
