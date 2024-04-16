package my.reservetable.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.reservetable.member.dto.MemberResponse;
import my.reservetable.member.service.SignupService;
import my.reservetable.member.dto.SignupRequest;
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

    @PostMapping("/signup/owner")
    public MemberResponse signUpOwner(@Valid @RequestBody SignupRequest request){
        return signupService.signupOwner(request);
    }

    @PostMapping("/signup/user")
    public MemberResponse signUpUser(@Valid @RequestBody SignupRequest request){
        return signupService.signupUser(request);
    }

    //todo : 패스워드 변경

}
