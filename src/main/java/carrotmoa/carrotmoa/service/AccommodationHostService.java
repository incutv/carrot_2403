package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.Post;
import carrotmoa.carrotmoa.model.request.CreateAccommodationRequest;
import carrotmoa.carrotmoa.model.request.UpdateAccommodationRequest;
import carrotmoa.carrotmoa.model.response.AccommodationDetailResponse;
import carrotmoa.carrotmoa.model.response.HostManagedAccommodationResponse;
import carrotmoa.carrotmoa.repository.AccommodationDetailCustomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AccommodationHostService {
    private final PostService postService;
    private final AccommodationService accommodationService;
    private final AccommodationSpaceService accommodationSpaceService;
    private final AccommodationAmenityService accommodationAmenityService;
    private final AccommodationImageService accommodationImageService;
    private final AccommodationDetailCustomRepository accommodationDetailCustomRepository;

    public AccommodationHostService(PostService postService,
                                    AccommodationService accommodationService,
                                    AccommodationSpaceService accommodationSpaceService,
                                    AccommodationAmenityService accommodationAmenityService,
                                    AccommodationImageService accommodationImageService,
                                    AccommodationDetailCustomRepository accommodationDetailCustomRepository) {
        this.postService = postService;
        this.accommodationService = accommodationService;
        this.accommodationSpaceService = accommodationSpaceService;
        this.accommodationAmenityService = accommodationAmenityService;
        this.accommodationImageService = accommodationImageService;
        this.accommodationDetailCustomRepository = accommodationDetailCustomRepository;
    }

    @Transactional
    public Long createAccommodation(CreateAccommodationRequest createAccommodationRequest) {
        try {
            Post savedPost = postService.savePost(createAccommodationRequest);
            Accommodation savedAccommodation = accommodationService.saveAccommodation(createAccommodationRequest, savedPost.getId());
            accommodationSpaceService.saveAccommodationSpaces(createAccommodationRequest.toAccommodationSpaceEntities(), savedAccommodation.getId());
            accommodationAmenityService.saveAmenities(savedAccommodation.getId(), createAccommodationRequest.getAmenityIds());
            accommodationImageService.saveAccommodationImages(savedAccommodation.getId(), createAccommodationRequest.getImages());
            return savedAccommodation.getId();
        } catch (IOException e) {
            log.error("Image upload failed: {}", e.getMessage());
            throw new RuntimeException("image upload failed", e);
        }

    }

    @Transactional
    public void updateAccommodation(Long accommodationId, UpdateAccommodationRequest updateAccommodationRequest, List<String> existingImageUrls)
            throws IOException {
        accommodationService.updateAccommodation(accommodationId, updateAccommodationRequest);
        postService.updatePost(accommodationId, updateAccommodationRequest);
        accommodationSpaceService.updateAccommodationSpaces(accommodationId, updateAccommodationRequest);
        accommodationAmenityService.updateAccommodationAmenities(accommodationId, updateAccommodationRequest);
        accommodationImageService.updateAccommodationImages(accommodationId, updateAccommodationRequest.getImages(), existingImageUrls);
    }


    public AccommodationDetailResponse getAccommodationDetail(Long id) {
        return accommodationDetailCustomRepository.getAccommodationDetailById(id);
    }


    public List<HostManagedAccommodationResponse> getAccommodationsByUserId(Long userId) {
        return accommodationDetailCustomRepository.findAccommodationsByUserId(userId);
    }
}
