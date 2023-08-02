package instagram.api.user.dto.response;

import instagram.api.user.dto.UserData;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
public class FollowingResponse {
    private List<UserData> users;
    private int totalPage;
    private int currentPage;

    public FollowingResponse(PageImpl<UserData> followerUsers) {
        this.users = followerUsers.getContent();
        this.totalPage = followerUsers.getTotalPages();
        this.currentPage = followerUsers.getNumber();
    }
}
