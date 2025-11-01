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

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        user = User.create("test@test.com", "Test1234!", "testNickname", "");
        post = Post.create(user, "testTitle", "testContent", "");
        userRepository.save(user);
        postRepository.save(post);
    }

    @AfterEach
    void tearDown() {
        postLikeRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @RepeatedTest(value = 10)
    void postLike_ThreadSafe() throws InterruptedException {
        int loopCount = 99;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                postLikeCreateOrUpdateService.createOrUpdate(user.getId(), post.getId());
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

        int likeCount = loopCount % 2 != 0 ? 1 : 0;
        PostLike postLike = postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId()).orElseThrow();
        Post foundPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(postLike.isLiked()).isTrue();
        assertThat(foundPost.getLikeCount()).isEqualTo(likeCount);
    }
}