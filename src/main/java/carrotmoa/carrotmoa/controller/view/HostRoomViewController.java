package carrotmoa.carrotmoa.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RestController// 쓰면 오류
@RequestMapping("/host/room")
@Slf4j
public class HostRoomViewController {

    // 방 등록
    @GetMapping("/register")
    public String showRegisterForm() {
        return "host/room_register";
    }

    // 방 관리 이동
    @GetMapping("/manage")
    public String getRoomManage() {
        return "host/room_manage";
    }

    // 방 수정
    @GetMapping("/edit/{accommodationId}")
    public String showEditRoomForm(@PathVariable("accommodationId") Long accommodationId) {
        return "host/room_edit";
    }
}
