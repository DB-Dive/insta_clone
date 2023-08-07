package instagram.api.feed.dto;

import lombok.Getter;

@Getter
public class StatusDto {
    private Long id;
    private Long feedId;

    public StatusDto(Long id, Long feedId) {
        this.id = id;
        this.feedId = feedId;
    }

    @Override
    public String toString() {
        return "StatusDto{" +
                "id=" + id +
                ", feedId=" + feedId +
                '}';
    }
}
