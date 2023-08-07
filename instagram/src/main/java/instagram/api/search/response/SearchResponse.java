package instagram.api.search.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchResponse {
    private List<SearchUserResponse> users;
    private List<SearchTagResponse> tags;

    @Builder
    public SearchResponse(List<SearchUserResponse> users, List<SearchTagResponse> tags) {
        this.users = users;
        this.tags = tags;
    }
}
