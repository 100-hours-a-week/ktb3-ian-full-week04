package ktb3.full.community.service;

import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.CommentCreateRequest;
import ktb3.full.community.dto.response.CommentResponse;
import ktb3.full.community.repository.CommentRepository;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    private final User user = User.create("test@test.com", "Test1234!", "testName", "");
    private final Post post = Post.create(user, "testTitle", "testContent", "");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        postRepository.save(post);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @RepeatedTest(value = 10)
    void create_ThreadSafe() throws InterruptedException {
        int loopCount = 10;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                CommentCreateRequest request = new CommentCreateRequest("testContent");
                commentService.createComment(user.getId(), post.getId(), request);
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
        assertThat(foundPost.getCommentCount()).isEqualTo(expectedSize);
        assertThat(commentRepository.findAll().stream()
                .filter(comment -> comment.getPost().getId().equals(foundPost.getId()))).hasSize(expectedSize);
    }

    @RepeatedTest(value = 10)
    void createAndDelete_ThreadSafe() throws InterruptedException {
        int loopCount = 10;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                CommentCreateRequest request = new CommentCreateRequest("testContent");
                CommentResponse response = commentService.createComment(user.getId(), post.getId(), request);
                commentService.deleteComment(response.getUserId(), response.getCommentId());
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

        Post foundPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(foundPost.getCommentCount()).isEqualTo(0);
        assertThat(commentRepository.findAll().stream()
                .filter(comment -> comment.getPost().getId().equals(foundPost.getId()) && !comment.isDeleted())).hasSize(0);
    }
}