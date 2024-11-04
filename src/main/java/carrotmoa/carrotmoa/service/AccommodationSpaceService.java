package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import carrotmoa.carrotmoa.model.request.AccommodationSpaceRequest;
import carrotmoa.carrotmoa.model.request.UpdateAccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationSpaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccommodationSpaceService {
    private final AccommodationSpaceRepository accommodationSpaceRepository;

    public AccommodationSpaceService(AccommodationSpaceRepository accommodationSpaceRepository) {
        this.accommodationSpaceRepository = accommodationSpaceRepository;
    }

    @Transactional
    public void saveAccommodationSpaces(List<AccommodationSpace> accommodationSpaces, Long accommodationId) {
        accommodationSpaces.forEach(accommodationSpace -> saveAccommodationSpace(accommodationSpace, accommodationId));
    }

    private void saveAccommodationSpace(AccommodationSpace accommodationSpace, Long accommodationId) {
        accommodationSpace.setAccommodationId(accommodationId);
        accommodationSpaceRepository.save(accommodationSpace);
        log.info("Saved accommodation space: {}", accommodationSpace);
    }

    @Transactional
    public void updateAccommodationSpaces(Long accommodationId, UpdateAccommodationRequest updateAccommodationRequest) {
        // 기존 값 가져오기
        List<AccommodationSpace> currentAccommodationSpaces = accommodationSpaceRepository.findByAccommodationId(accommodationId);

        // 기존 공간들을 Map으로 변환 (spaceId와 count만 저장)
        Map<Long, Integer> currentSpaceMap = currentAccommodationSpaces.stream()
                .collect(Collectors.toMap(AccommodationSpace::getSpaceId, AccommodationSpace::getCount));

        // 요청으로부터 새로운 값 가져오기
        List<AccommodationSpaceRequest> newAccommodationSpaces = updateAccommodationRequest.getAccommodationSpaces();

        // 요청에 포함된 공간들을 업데이트
        for (AccommodationSpaceRequest spaceRequest : newAccommodationSpaces) {
            Long spaceId = spaceRequest.getSpaceId();
            Integer count = spaceRequest.getCount();

            // spaceId와 count가 모두 null이 아닐 경우에만 처리
            if (spaceId != null && count != null) {
                Integer currentCount = currentSpaceMap.get(spaceId);
                if (currentCount != null) {
                    // 기존 개수를 변경
                    log.info("공간 ID {}의 개수 변경: {} -> {}", spaceId, currentCount, count);
                    // 엔티티의 update 메서드 호출
                    currentAccommodationSpaces.stream()
                            .filter(space -> space.getSpaceId().equals(spaceId))
                            .findFirst()
                            .ifPresent(space -> space.updateAccommodationSpace(count));
                } else {
                    log.warn("공간 ID {}는 기존 공간 리스트에 존재하지 않습니다.", spaceId);
                }
            }
        }

        // 변경 사항 저장
        accommodationSpaceRepository.saveAll(currentAccommodationSpaces);

    }


}
