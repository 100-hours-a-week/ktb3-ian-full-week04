package ktb3.full.community.service.impl;

import ktb3.full.community.domain.Comment;
import ktb3.full.community.domain.Post;
import ktb3.full.community.domain.User;
import ktb3.full.community.dto.request.CommentCreateRequest;
import ktb3.full.community.dto.response.CommentResponse;
import ktb3.full.community.infrastructure.database.table.*;
import ktb3.full.community.repository.PostLikeRepository;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.repository.UserRepository;
import ktb3.full.community.infrastructure.database.identifier.LongIdentifierGenerator;
import ktb3.full.community.repository.impl.CommentMemoryRepository;
import ktb3.full.community.repository.impl.PostLikeMemoryRepository;
import ktb3.full.community.repository.impl.PostMemoryRepository;
import ktb3.full.community.repository.impl.UserMemoryRepository;
import ktb3.full.community.service.CommentService;
import ktb3.full.community.service.PostService;
import ktb3.full.community.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class CommentServiceImplTest {

    private final LongIdentifierGenerator<User> userIdentifierGenerator = new LongIdentifierGenerator<>();
    private final LongIdentifierGenerator<Post> postIdentifierGenerator = new LongIdentifierGenerator<>();
    private final LongIdentifierGenerator<Comment> commentIdentifierGenerator = new LongIdentifierGenerator<>();
    private final InMemoryUserTable userTable = new InMemoryUserTable(userIdentifierGenerator);
    private final PostCommentRelationManager postCommentRelationManager = new PostCommentRelationManager();
    private final InMemoryPostTable postTable = new InMemoryPostTable(postIdentifierGenerator, postCommentRelationManager);
    private final InMemoryCommentTable commentTable = new InMemoryCommentTable(commentIdentifierGenerator, postCommentRelationManager);
    private final InMemoryPostLikeTable postLikeTable = new InMemoryPostLikeTable(null);
    private final UserRepository userRepository = new UserMemoryRepository(userTable);
    private final PostRepository postRepository = new PostMemoryRepository(postTable);
    private final PostLikeRepository postLikeRepository = new PostLikeMemoryRepository(postLikeTable);
    private final UserService userService = new UserServiceImpl(userRepository);
    private final PostService postService = new PostServiceImpl(postRepository, postLikeRepository, userService);
    private final CommentMemoryRepository commentRepository = new CommentMemoryRepository(commentTable);
    private final CommentService commentService = new CommentServiceImpl(commentRepository, userService, postService);
    private final User user = User.create("test@test.com", "Test1234!", "testNickname", "");
    private final Post post = Post.create(user, "testTitle", "testContent", "");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        postRepository.save(post);
    }

    @RepeatedTest(value = 100)
    void create_ThreadSafe() throws InterruptedException {
        int loopCount = 100;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                CommentCreateRequest request = new CommentCreateRequest("testContent");
                commentService.createComment(user.getUserId(), post.getPostId(), request);
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
        assertThat(post.getCommentCount()).isEqualTo(expectedSize);
        assertThat(commentRepository.findAllByPostId(post.getPostId())).hasSize(expectedSize);
    }

    @RepeatedTest(value = 100)
    void createAndDelete_ThreadSafe() throws InterruptedException {
        int loopCount = 100;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                CommentCreateRequest request = new CommentCreateRequest("testContent");
                CommentResponse response = commentService.createComment(user.getUserId(), post.getPostId(), request);
                commentService.deleteComment(response.getUserId(), response.getCommentId());
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

        assertThat(post.getCommentCount()).isEqualTo(0);
        assertThat(commentRepository.findAllByPostId(post.getPostId())).hasSize(0);
    }
}