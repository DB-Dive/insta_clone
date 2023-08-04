package instagram.api.feed.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentsDto {
    private List<CommentDto> data = new ArrayList<>();
    private int totalPage;
    private int currentPage;

    public CommentsDto(List<CommentDto> data, int totalPage, int currentPage) {
        this.data = data;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "CommentsDto{" +
                "data=" + data +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                '}';
    }
}
