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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    void viewCount_ThreadSafe() {
        int numThread = 10;
        try (ExecutorService executor = Executors.newFixedThreadPool(numThread)) {
            for (int i = 0; i < numThread; i++) {
                executor.submit(() -> postService.getPost(user.getId(), post.getId()));
            }
        }

        Post foundPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(foundPost.getViewCount()).isEqualTo(numThread);
    }
}