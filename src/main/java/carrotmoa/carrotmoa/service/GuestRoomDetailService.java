package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Space;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.AmenityImageResponse;
import carrotmoa.carrotmoa.model.response.SpaceImageResponse;
import carrotmoa.carrotmoa.model.response.UserProfileResponse;
import carrotmoa.carrotmoa.repository.AccommodationAmenityRepository;
import carrotmoa.carrotmoa.repository.AccommodationDetailCustomRepository;
import carrotmoa.carrotmoa.repository.SpaceRepository;
import carrotmoa.carrotmoa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GuestRoomDetailService {
    public final UserRepository userRepository;
    private final AccommodationAmenityRepository accommodationAmenityRepository;
    private final AccommodationDetailCustomRepository accommodationDetailCustomRepository;
    private final SpaceRepository spaceRepository;

    public GuestRoomDetailService(UserRepository userRepository, AccommodationAmenityRepository accommodationAmenityRepository,
                                  AccommodationDetailCustomRepository accommodationDetailCustomRepository, SpaceRepository spaceRepository) {
        this.userRepository = userRepository;
        this.accommodationAmenityRepository = accommodationAmenityRepository;
        this.accommodationDetailCustomRepository = accommodationDetailCustomRepository;
        this.spaceRepository = spaceRepository;
    }

    @Transactional
    public List<UserProfileResponse> getHostProfile(Long id) {
        List<Object[]> profile = userRepository.getUserProfile(id);
        return profile.stream()
                .map(UserProfileResponse::fromData)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AmenityImageResponse> getAmenityImage(Long id) {
        List<Object[]> result = accommodationAmenityRepository.findAccommodationAmenitiesByAccommodationId(id);
        return result.stream()
                .map(AmenityImageResponse::fromData)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccommodationDetailResponse getAccommodationDetail(Long id) {
        return accommodationDetailCustomRepository.getAccommodationDetailById(id);
    }

    @Transactional
    public List<SpaceImageResponse> getSpaceImage() {
        List<SpaceImageResponse> icons = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Optional<Space> space = spaceRepository.findById((long) i);
            if (space.isPresent()) {
                icons.add(new SpaceImageResponse(space.get().getIcon()));
            }
        }
        return icons;
    }
}
