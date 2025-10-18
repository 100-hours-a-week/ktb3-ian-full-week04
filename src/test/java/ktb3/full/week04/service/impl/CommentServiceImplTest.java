package ktb3.full.week04.service.impl;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.PostLike;
import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.request.CommentCreateRequest;
import ktb3.full.week04.dto.response.CommentResponse;
import ktb3.full.week04.infrastructure.database.table.Table;
import ktb3.full.week04.repository.PostLikeRepository;
import ktb3.full.week04.repository.PostRepository;
import ktb3.full.week04.repository.UserRepository;
import ktb3.full.week04.infrastructure.database.identifier.LongIdentifierGenerator;
import ktb3.full.week04.repository.impl.CommentMemoryRepository;
import ktb3.full.week04.repository.impl.PostLikeMemoryRepository;
import ktb3.full.week04.repository.impl.PostMemoryRepository;
import ktb3.full.week04.repository.impl.UserMemoryRepository;
import ktb3.full.week04.service.CommentService;
import ktb3.full.week04.service.PostService;
import ktb3.full.week04.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class CommentServiceImplTest {

    private final LongIdentifierGenerator<User> userIdentifierGenerator = new LongIdentifierGenerator<>();
    private final LongIdentifierGenerator<Post> postIdentifierGenerator = new LongIdentifierGenerator<>();
    private final LongIdentifierGenerator<Comment> commentIdentifierGenerator = new LongIdentifierGenerator<>();
    private final Table<User, Long> userTable = new Table<>(userIdentifierGenerator);
    private final Table<Post, Long> postTable = new Table<>(postIdentifierGenerator);
    private final Table<Comment, Long> commentTable = new Table<>(commentIdentifierGenerator);
    private final Table<PostLike, PostLikeMemoryRepository.UserAndPostId> postLikeTable = new Table<>(null);
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
//                assertThat(post.getCommentCount()).isEqualTo(0);
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
        assertThat(commentRepository.findAllByPostId(post.getPostId())).hasSize(numThreads * loopCount);
    }
}