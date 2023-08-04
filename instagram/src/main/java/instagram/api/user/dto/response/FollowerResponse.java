package instagram.api.user.dto.response;

import instagram.api.user.dto.UserDto;
import lombok.Getter;

import java.util.List;

@Getter
public class FollowerResponse {
    private List<UserDto> users;
    private int totalPage;
    private int currentPage;

    public FollowerResponse(List<UserDto> userDtos, int totalPage, int currentPage) {
        this.users = userDtos;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }
}
