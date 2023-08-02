package instagram.entity.user;

import lombok.Getter;

@Getter
public enum UserEnum {
    ADMIN("관리자"), USER("사용자");
    private String value;

    UserEnum(String value) {
        this.value = value;
    }
}
