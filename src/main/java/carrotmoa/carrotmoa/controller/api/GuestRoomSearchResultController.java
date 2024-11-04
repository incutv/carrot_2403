package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.response.AccommodationResultResponse;
import carrotmoa.carrotmoa.service.AccommodationSearchResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/guest")
@Slf4j
public class GuestRoomSearchResultController {
    @Autowired
    AccommodationSearchResultService accommodationSearchResultService;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<AccommodationResultResponse> rooms = accommodationSearchResultService.searchAccommodations(keyword);
        model.addAttribute("rooms", rooms);
        model.addAttribute("keyword", keyword);
        return "guest/roomResult";
    }

//    @GetMapping("/{keyword}")
//    public ResponseEntity<List<AccommodationResultResponse>> searchRoomByKeyword(@PathVariable("keyword") String keyword) {
//        List<AccommodationResultResponse> accommodationResultResponseList = accommodationSearchResultService.searchAccommodations(keyword);
//        if(accommodationResultResponseList == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(accommodationResultResponseList);
//    }
}
