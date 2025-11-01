package ktb3.full.community.service;

import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = User.create("test@test.com", "Test1234!", "testName", "");
        post = Post.create(user, "testTitle", "testContent", "");
        userRepository.save(user);
        postRepository.save(post);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @RepeatedTest(value = 10)
    void viewCount_ThreadSafe() throws InterruptedException {
        int loopCount = 10;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                postService.getPost(user.getId(), post.getId());
            }
        };

        int numThreads = 3;
        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);
        Thread threadC = new Thread(runnable);

        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        int expectedSize = loopCount * numThreads;
        Post foundPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(foundPost.getViewCount()).isEqualTo(expectedSize);
    }
}