package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.SaveCommunityPostRequest;
import carrotmoa.carrotmoa.model.request.UpdateCommunityPostRequest;
import carrotmoa.carrotmoa.model.response.CommunityCategoryResponse;
import carrotmoa.carrotmoa.model.response.CommunityCategoryResponses;
import carrotmoa.carrotmoa.model.response.CommunityPostDetailResponse;
import carrotmoa.carrotmoa.model.response.CommunityPostListResponse;
import carrotmoa.carrotmoa.service.CommunityCategoryService;
import carrotmoa.carrotmoa.service.CommunityPostService;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    final private CommunityPostService communityPostService;
    final private CommunityCategoryService categoriesService;

    @GetMapping("/posts")
    public ResponseEntity<Slice<CommunityPostListResponse>> getAllCommunityPosts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Slice<CommunityPostListResponse> allCommunityPosts = communityPostService.getAllCommunityPosts(page, size);
        return new ResponseEntity<>(allCommunityPosts, HttpStatus.OK);
    }

     @GetMapping("/posts/subCategories/{subcategoryId}")
    public ResponseEntity<Slice<CommunityPostListResponse>> getPostsBySubCategory(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable(name = "subcategoryId") Long subcategoryId) {
         Slice<CommunityPostListResponse> postsBySubCategory = communityPostService.getPostsBySubCategory(subcategoryId, page, size);
         return new ResponseEntity<>(postsBySubCategory, HttpStatus.OK);
    }


    @PostMapping("/posts")
    public ResponseEntity<Long> createCommunityPost(@RequestBody SaveCommunityPostRequest saveCommunityPostRequest) {
        log.info("게시글 삽입 데이터 확인: {}", saveCommunityPostRequest);
        Long postId = communityPostService.createCommunityPost(saveCommunityPostRequest);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    @GetMapping("/sub-categories")
    public ResponseEntity<CommunityCategoryResponses> getSubCategories() {
        CommunityCategoryResponses subCategories = categoriesService.getSubCategories();
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<CommunityCategoryResponses> getAllCategories() {
        CommunityCategoryResponses categories = categoriesService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/posts/{communityPostId}")
    public ResponseEntity<CommunityPostDetailResponse> findCommunityPostByPostId(@PathVariable("communityPostId") Long id, HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        CommunityPostDetailResponse communityPostByPostId = communityPostService.findCommunityPostDetail(id);
        return new ResponseEntity<>(communityPostByPostId, HttpStatus.OK);
    }

    @PutMapping("/posts/{communityPostId}")
        public ResponseEntity<Long> updateCommunityPost(@PathVariable("communityPostId") Long communityPostId, @RequestBody UpdateCommunityPostRequest request) {
        Long updateCommunityPostId = communityPostService.updateCommunityPost(communityPostId, request);
        return new ResponseEntity<>(updateCommunityPostId, HttpStatus.OK);
    }



    @DeleteMapping("/posts/{communityPostId}")
    public ResponseEntity<Integer> deleteByCommunityPostId(@PathVariable("communityPostId") Long communityPostId) {
        int deletedCount = communityPostService.deleteByCommunityPostId(communityPostId);
        return new ResponseEntity<>(deletedCount, HttpStatus.OK);
    }
}
