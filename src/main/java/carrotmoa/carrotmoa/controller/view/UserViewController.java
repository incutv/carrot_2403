package carrotmoa.carrotmoa.controller.view;

import carrotmoa.carrotmoa.model.request.UserJoinDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserViewController {

    @GetMapping("/login-page")
    public String userLoginForm() {
        return "user/login-page";
    }

    @GetMapping("/join-form")
    public String userJoinForm(Model model) {
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "user/join";
    }

    @GetMapping("/mypage")
    public String userMyPage() {
        return "mypage";
    }

    @GetMapping("/test")
    public String test() {
        return "user/test";
    }
}


