package carrotmoa.carrotmoa.config;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserAttribute {

    @ModelAttribute("user")
    public UserDetails user(@AuthenticationPrincipal UserDetails user) {
        return user;
    }


}
