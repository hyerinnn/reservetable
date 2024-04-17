package my.reservetable.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class LoginController {

    @GetMapping("/owner")
    public String accessTest1(){
        return "owner 페이지 ";
    }
    @GetMapping("/user")
    public String accessTest2(){
        return "user 페이지";
    }

}
