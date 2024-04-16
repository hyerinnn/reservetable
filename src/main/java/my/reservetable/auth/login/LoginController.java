package my.reservetable.auth.login;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login/main")
    public String loginPage(){
        return "로그인이 필요합니다.";
    }

    @PostMapping("/login")
    public String login(LoginRequest request){
        return loginService.login(request);
    }

}