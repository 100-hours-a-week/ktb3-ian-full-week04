package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.User;
import ktb3.full.week04.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostMemoryRepositoryTest {

    private final UserRepository userRepository = new UserMemoryRepository();
    private final PostMemoryRepository postRepository = new PostMemoryRepository();
    private final User user = User.create("test@test.com", "Test1234!", "testNickname", "");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @RepeatedTest(value = 100)
    void save_ThreadSafe() throws InterruptedException {
        int loopCount = 100;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                postRepository.save(Post.create(user, "testTitle" + i, "testContent" + i, ""));
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
        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(expectedSize);
        for (int i = 0; i < posts.size() - 1; i++) {
            assertThat(posts.get(i).getCreatedAt()).isBeforeOrEqualTo(posts.get(i + 1).getCreatedAt());
        }
    }
}