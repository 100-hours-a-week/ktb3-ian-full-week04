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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    void create_ThreadSafe() {
        CommentCreateRequest request = new CommentCreateRequest("testContent");

        int numThread = 10;
        try (ExecutorService executor = Executors.newFixedThreadPool(numThread)) {
            for (int i = 0; i < numThread; i++) {
                executor.submit(() -> commentService.createComment(user.getId(), post.getId(), request));
            }
        }

        Post foundPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(foundPost.getCommentCount()).isEqualTo(numThread);
        assertThat(commentRepository.findAll().stream()
                .filter(comment -> comment.getPost().getId().equals(foundPost.getId()))).hasSize(numThread);
    }

    @RepeatedTest(value = 10)
    void createAndDelete_ThreadSafe() {
        CommentCreateRequest request = new CommentCreateRequest("testContent");

        int numThread = 10;
        try (ExecutorService executor = Executors.newFixedThreadPool(numThread)) {
            for (int i = 0; i < numThread; i++) {
                CommentResponse response = commentService.createComment(user.getId(), post.getId(), request);
                executor.submit(() -> commentService.deleteComment(response.getUserId(), response.getCommentId()));
            }
        }

        Post foundPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(foundPost.getCommentCount()).isEqualTo(0);
        assertThat(commentRepository.findAll().stream()
                .filter(comment -> comment.getPost().getId().equals(foundPost.getId()) && !comment.isDeleted())).hasSize(0);
    }
}