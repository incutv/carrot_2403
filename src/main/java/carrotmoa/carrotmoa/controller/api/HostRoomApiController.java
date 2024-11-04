package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.CreateAccommodationRequest;
import carrotmoa.carrotmoa.model.request.UpdateAccommodationRequest;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import carrotmoa.carrotmoa.service.AccommodationHostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/host/room")
public class HostRoomApiController {
    // 기본 생성 공간 수 (방, 화장실, 거실, 주방)
    private static final int DEFAULT_SPACE_COUNT = 4;

    @Autowired
    AccommodationHostService accommodationHostService;

    // 방 등록 폼 제출 후 테이블에 값 들어옴
    @PostMapping("/register")
    public ResponseEntity<?> registerAccommodation(
            @Valid @ModelAttribute CreateAccommodationRequest createAccommodationRequest,
            BindingResult bindingResult) {

        // 유효성 검사 실패 시 처리
        if (bindingResult.hasErrors()) {
            //오류 메시지를 포함한 응답 반환
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }

        // 공간 초기화 메서드 호출
        if (createAccommodationRequest.getAccommodationSpaces().isEmpty()) {
            createAccommodationRequest.initializeSpaces(DEFAULT_SPACE_COUNT);
        }
        createAccommodationRequest.setUserId(3L);

        // 입력한 거 로그 찍기
        createAccommodationRequest.logRequestDetails();

        Long accommodationId = accommodationHostService.createAccommodation(createAccommodationRequest);
        return new ResponseEntity<>(accommodationId, HttpStatus.CREATED);
    }

    // 숙소 정보 업데이트 (PATCH 요청)
    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> updateAccommodation(
            @PathVariable("id") Long id,
            @ModelAttribute UpdateAccommodationRequest updateAccommodationRequest,
            @RequestParam(value = "existingImageUrls", required = false) List<String> existingImageUrls) {

        log.info("기존 이미지 URL: {}", existingImageUrls);
        log.info("업로드된 이미지 개수: {}", (updateAccommodationRequest.getImages() != null ? updateAccommodationRequest.getImages().size() : 0));

        // 숙소 정보 업데이트
        try {
            accommodationHostService.updateAccommodation(id, updateAccommodationRequest, existingImageUrls);
            return ResponseEntity.ok("숙소 정보가 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("업데이트 중 오류가 발생했습니다.");
        }
    }


    // 방 상세
    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDetailResponse> getAccommodationDetail(@PathVariable("id") Long id) {
        AccommodationDetailResponse accommodationDetailResponse = accommodationHostService.getAccommodationDetail(id);
        if (accommodationDetailResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accommodationDetailResponse);
    }

    // 방 수정
//    @PostMapping("/edit/{id}")
//    public ResponseEntity<Long> updateRoom(@PathVariable Long id, @ModelAttribute Host)


    // 호스트가 등록한 방 리스트 보이기(방 관리)
    @GetMapping("/manage/{userId}")
    public ResponseEntity<List<HostManagedAccommodationResponse>> getAccommodationsByUserId(@PathVariable("userId") Long userId) {
        List<HostManagedAccommodationResponse> accommodations = accommodationHostService.getAccommodationsByUserId(userId);

        if (accommodations == null || accommodations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }

        return ResponseEntity.ok(accommodations);
    }

//    @PostMapping("/test-validation")
//    public ResponseEntity<?> testValidation(@Valid @RequestBody CreateAccommodationRequest createAccommodationRequest, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
//        }
//        return ResponseEntity.ok("Validation passed");
//    }


}
