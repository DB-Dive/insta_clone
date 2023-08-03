package instagram.api.feed.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor @Getter @Setter
public class BookmarkDto {
    @NotNull
    private Long feedId;
    private Long userId;
}
