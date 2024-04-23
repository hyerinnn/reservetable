package my.reservetable.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.MemberResponse;
import my.reservetable.member.dto.SignupRequest;
import my.reservetable.member.service.SignupService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class SignupController {

    private final SignupService signupService;

    @GetMapping("/signup/main")
    public String signUpPage(){
        return "회원가입 페이지 입니다.";
    }

    @Operation(summary = "사장님 회원가입")
    @PostMapping("/signup/owner")
    public MemberResponse signUpOwner(@Valid @RequestBody SignupRequest request){
        return signupService.signupOwner(request);
    }

    @Operation(summary = "일반 회원가입")
    @PostMapping("/signup/user")
    public MemberResponse signUpUser(@Valid @RequestBody SignupRequest request){
        return signupService.signupUser(request);
    }

    //todo : 패스워드 변경

}
