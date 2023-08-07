package instagram.api.search.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OnlyTagResponse {
    List<SearchTagResponse> tags;

    public OnlyTagResponse(List<SearchTagResponse> tags) {
        this.tags = tags;
    }
}
