package instagram.api.search.controller;

import instagram.api.search.response.OnlyTagResponse;
import instagram.api.search.response.SearchResponse;
import instagram.api.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{keyword}")
    public SearchResponse search(@PathVariable String keyword){
        return searchService.search(keyword);
    }

    @GetMapping("/tag/{keyword}")
    public OnlyTagResponse searchTag(@PathVariable String keyword){
        return searchService.searchTag(keyword);
    }
}
