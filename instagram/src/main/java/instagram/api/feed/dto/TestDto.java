package instagram.api.feed.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TestDto {
    private Long id;
    private String feedImage;
    private Long goodCnt;
    private Long commentCnt;

    @Override
    public String toString() {
        return "TestDto{" +
                "id=" + id +
                ", feedImage='" + feedImage + '\'' +
                ", goodCnt=" + goodCnt +
                ", commentCnt=" + commentCnt +
                '}';
    }

    public TestDto(Long id, String feedImage, Long goodCnt, Long commentCnt) {
        this.id = id;
        this.feedImage = feedImage;
        this.goodCnt = goodCnt;
        this.commentCnt = commentCnt;
    }
}
