package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.AmenityImageResponse;
import carrotmoa.carrotmoa.model.response.SpaceImageResponse;
import carrotmoa.carrotmoa.model.response.UserProfileResponse;
import carrotmoa.carrotmoa.service.GuestRoomDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/room/detail")
public class GuestRoomDetailController {
    @Autowired
    GuestRoomDetailService guestRoomDetailService;

    @GetMapping("/{id}")
    public String roomDetail(@PathVariable("id") long id, Model model) {
        AccommodationDetailResponse getRoomDetailById = guestRoomDetailService.getAccommodationDetail(id);
        List<AmenityImageResponse> amenities = guestRoomDetailService.getAmenityImage(id);
        List<SpaceImageResponse> icons = guestRoomDetailService.getSpaceImage();
        List<UserProfileResponse> profile = guestRoomDetailService.getHostProfile(id);
        model.addAttribute("room", getRoomDetailById);
        model.addAttribute("amenities", amenities);
        model.addAttribute("icons", icons);
        model.addAttribute("profile", profile);
        return "guest/roomDetail";
    }

//    @GetMapping("/{id}") // postman test
//    public List<UserProfileResponse> getAmenities(@PathVariable("id") long id) {
//        AccommodationDetailResponse getRoomDetailById = guestRoomDetailService.getAccommodationDetail(id);
//        List<AmenityImageResponse> amenities = guestRoomDetailService.getAmenityImage(id);
//        List<SpaceImageResponse> icons = guestRoomDetailService.getSpaceImage();
//        List<UserProfileResponse> profile = guestRoomDetailService.getHostProfile(id);
//        return profile;
//    }
}
