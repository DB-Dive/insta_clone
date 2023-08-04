package instagram.entity.feed;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagname;

    private Long feedId;

    public HashTag(String tagname) {
        this.tagname = tagname;
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "id=" + id +
                ", tagname='" + tagname + '\'' +
                ", feedId=" + feedId +
                '}';
    }

    @Builder
    public HashTag(String tagname, Long feedId) {
        this.tagname = tagname;
        this.feedId = feedId;
    }
}