package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationAmenity;
import carrotmoa.carrotmoa.model.request.UpdateAccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationAmenityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccommodationAmenityService {
    private final AccommodationAmenityRepository accommodationAmenityRepository;

    public AccommodationAmenityService(AccommodationAmenityRepository accommodationAmenityRepository) {
        this.accommodationAmenityRepository = accommodationAmenityRepository;
    }

    @Transactional
    public void saveAmenities(Long accommodationId, List<Long> amenityIds) {
        if (amenityIds != null) {
            amenityIds.forEach(amenityId -> {
                AccommodationAmenity accommodationAmenity = new AccommodationAmenity();
                accommodationAmenity.setAccommodationId(accommodationId);
                accommodationAmenity.setAmenityId(amenityId);
                accommodationAmenityRepository.save(accommodationAmenity);
            });
        }
    }

    @Transactional
    public void updateAccommodationAmenities(Long accommodationId, UpdateAccommodationRequest updateAccommodationRequest) {
        // 새로운 amenityId 목록을 가져옴
        List<Long> newAmenityIds = updateAccommodationRequest.getAmenityIds();

        // 기존의 편의 시설 목록을 가져옴
        List<AccommodationAmenity> existingAmenities = accommodationAmenityRepository.findByAccommodationId(accommodationId);

        // 기존의 amenityId 목록 생성
        Set<Long> existingAmenityIds = existingAmenities.stream()
                .map(AccommodationAmenity::getAmenityId)
                .collect(Collectors.toSet());

        // 새로 추가할 amenityId 목록
        List<Long> amenitiesToAdd = newAmenityIds.stream()
                .filter(amenityId -> !existingAmenityIds.contains(amenityId))
                .toList();

        // 삭제할 amenityId 목록
        List<Long> amenitiesToRemove = existingAmenityIds.stream()
                .filter(amenityId -> !newAmenityIds.contains(amenityId))
                .toList();

        // 새로운 편의 시설 추가
        for (Long amenityId : amenitiesToAdd) {
            AccommodationAmenity accommodationAmenity = AccommodationAmenity.builder()
                    .accommodationId(accommodationId)
                    .amenityId(amenityId)
                    .build();
            accommodationAmenityRepository.save(accommodationAmenity);
        }

        // 기존의 편의 시설 삭제
        for (Long amenityId : amenitiesToRemove) {
            accommodationAmenityRepository.deleteByAccommodationIdAndAmenityId(accommodationId, amenityId);
        }


    }


}
