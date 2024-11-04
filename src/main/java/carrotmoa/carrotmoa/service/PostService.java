package carrotmoa.carrotmoa.service;

import carrotmoa.carrotmoa.entity.Accommodation;
import carrotmoa.carrotmoa.entity.Post;
import carrotmoa.carrotmoa.model.request.CreateAccommodationRequest;
import carrotmoa.carrotmoa.model.request.UpdateAccommodationRequest;
import carrotmoa.carrotmoa.repository.AccommodationRepository;
import carrotmoa.carrotmoa.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final AccommodationRepository accommodationRepository;

    public PostService(PostRepository postRepository, AccommodationRepository accommodationRepository) {
        this.postRepository = postRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Transactional
    public Post savePost(CreateAccommodationRequest createAccommodationRequest) {
        Post post = createAccommodationRequest.toPostEntity();
        return postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long accommodationId, UpdateAccommodationRequest updateAccommodationRequest) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));

        Post post = postRepository.findById(accommodation.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("포스트를 찾을 수 없습니다."));

        log.info("방 이름 및 설명 변경 전: 제목: {}, 내용: {}", post.getTitle(), post.getContent());

        // update 메서드 호출
        post.updatePost(updateAccommodationRequest.getTitle(), updateAccommodationRequest.getContent());

        log.info("방 이름 및 설명 변경 후: 제목: {}, 내용: {}", post.getTitle(), post.getContent());

        postRepository.save(post);
    }


}
