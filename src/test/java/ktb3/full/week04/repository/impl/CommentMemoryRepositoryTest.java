package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.User;
import ktb3.full.week04.repository.PostRepository;
import ktb3.full.week04.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommentMemoryRepositoryTest {

    private final UserRepository userRepository = new UserMemoryRepository();
    private final PostRepository postRepository = new PostMemoryRepository();
    private final CommentMemoryRepository commentMemoryRepository = new CommentMemoryRepository();
    private final User user = User.create("test@test.com", "Test1234!", "testNickname", "");
    private final Post post = Post.create(user, "testTitle", "testContent", "");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        postRepository.save(post);
    }

    @RepeatedTest(value = 10)
    void save_ThreadSafe() throws InterruptedException {
        int loopCount = 10;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                commentMemoryRepository.save(Comment.create(user, post, "testComment" + i));
            }
        };

        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);
        Thread threadC = new Thread(runnable);

        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        int expectedSize = loopCount * 3;
        List<Comment> comments = commentMemoryRepository.findAllByPostId(post.getPostId());

        assertThat(comments.size()).isEqualTo(expectedSize);
        for (int i = 0; i < comments.size() - 1; i++) {
            assertThat(comments.get(i).getCreatedAt()).isBeforeOrEqualTo(comments.get(i + 1).getCreatedAt());
        }
    }
}