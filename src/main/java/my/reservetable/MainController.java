package my.reservetable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root(){
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "shop/shops";
    }

    @GetMapping("/owner")
    public String owner() {
        return "owner/owner";
    }

    @GetMapping("/user")
    public String user() {
        return "user/user";
    }

}
