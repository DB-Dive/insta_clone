package instagram.api.feed.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {
    private Long userId;
    private String userProfileImage;
    private String username;

    private String content;
    private LocalDateTime createdAt;

    @Builder
    public CommentDto(Long userId, String userProfileImage, String username, String content, LocalDateTime createdAt) {
        this.userId = userId;
        this.userProfileImage = userProfileImage;
        this.username = username;

        this.content = content;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "userId=" + userId +
                ", userProfileImage='" + userProfileImage + '\'' +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
