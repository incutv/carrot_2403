package carrotmoa.carrotmoa.controller.api;

import carrotmoa.carrotmoa.model.request.SaveFleaMarketPostRequest;
import carrotmoa.carrotmoa.model.response.FleaMarketPostResponse;
import carrotmoa.carrotmoa.service.FleaMarketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "중고거래", description = "중고거래 페이지")
@RequestMapping("/api/fleamarket")
@RestController
@RequiredArgsConstructor
public class FleaMarketController {

    private final FleaMarketService fleaMarketService;

    //TODO : 로그인된 유저의 id를 가져오는 과정 추가 필요

    @PostMapping
    public Long createPost(@RequestBody SaveFleaMarketPostRequest saveFleaMarketPostRequest) {
        return fleaMarketService.savePost(saveFleaMarketPostRequest);
    }

    @GetMapping("/list")
    public List<FleaMarketPostResponse> getPostList() {
        return fleaMarketService.getPostList();
    }

    @GetMapping("/post")
    public FleaMarketPostResponse getPost(@RequestParam Long id) {
        return fleaMarketService.getPost(id);
    }
}
