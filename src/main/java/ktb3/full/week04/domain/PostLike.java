package ktb3.full.week04.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostLike {

    private final User user;
    private final Post post;
    private boolean liked;

    public static PostLike create(User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .liked(false)
                .build();
    }
}
