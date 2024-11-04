package carrotmoa.carrotmoa.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fleamarket")
@Controller
public class FleaMarketViewController {

    @GetMapping
    public String getFleaMarket() {
        return "flea-market";
    }

    @GetMapping("/posts")
    public String getPost() {
        return "flea-market-post";
    }
}
