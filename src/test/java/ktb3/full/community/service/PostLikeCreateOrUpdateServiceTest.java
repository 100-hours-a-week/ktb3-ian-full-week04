package ktb3.full.community.service;

import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.PostLike;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.repository.PostLikeRepository;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostLikeCreateOrUpdateServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostLikeCreateOrUpdateService postLikeCreateOrUpdateService;

    int numThread = 1000;
    private final List<User> users = new ArrayList<>();
    private Post post;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < numThread; i++) {
            users.add(User.create(i + "test@test.com", "Test1234!", "test" + i, ""));
        }
        post = Post.create(users.getFirst(), "testTitle", "testContent", "");
        userRepository.saveAll(users);
        postRepository.save(post);
    }

    @AfterEach
    void tearDown() {
        postLikeRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @RepeatedTest(value = 1)
    void postLike_ThreadSafe() {
        try (ExecutorService executor = Executors.newFixedThreadPool(numThread)) {
            for (int i = 0; i < numThread; i++) {
                int finalI = i;
                executor.submit(() -> postLikeCreateOrUpdateService.createOrUpdate(users.get(finalI).getId(), post.getId()));
            }
        }

        Post foundPost = postRepository.findById(post.getId()).orElseThrow();
        users.forEach(user -> {
            PostLike postLike = postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId()).orElseThrow();
            assertThat(postLike.isLiked()).isTrue();
        });
        assertThat(foundPost.getLikeCount()).isEqualTo(numThread);
    }
}