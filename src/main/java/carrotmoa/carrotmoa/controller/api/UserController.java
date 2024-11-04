package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.UserJoinDto;
import carrotmoa.carrotmoa.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email-check")
    public ResponseEntity<Boolean> emailCheck(@RequestParam("email") String email) {
        return new ResponseEntity<Boolean>(userService.emailCheck(email), HttpStatus.OK);
    }

    @GetMapping("/auth-code-send")
    public ResponseEntity<Boolean> authCodeEmailSend(@RequestParam("email") String email) {
        boolean result;
        try {
            userService.sendCodeToEmail(email);
            result = true;
        } catch (MailSendException e) {
            result = false;
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @GetMapping("/auth-code-certified")
    public ResponseEntity<Boolean> authCodeCertified(@RequestParam("email") String email, @RequestParam("inputauthcode") String inputAuthCode) {
        boolean result = userService.authCodeCertified(email, inputAuthCode);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<Boolean> userJoinSubmit(@RequestBody UserJoinDto userJoinDto) {
        boolean result = userService.userJoin(userJoinDto);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }


}
