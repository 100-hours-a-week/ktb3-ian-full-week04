package ktb3.full.community.service;

import ktb3.full.community.domain.Post;
import ktb3.full.community.domain.User;
import ktb3.full.community.infrastructure.database.table.*;
import ktb3.full.community.repository.PostLikeRepository;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.repository.UserRepository;
import ktb3.full.community.infrastructure.database.identifier.LongIdentifierGenerator;
import ktb3.full.community.repository.impl.PostLikeMemoryRepository;
import ktb3.full.community.repository.impl.PostMemoryRepository;
import ktb3.full.community.repository.impl.UserMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {

    private final LongIdentifierGenerator<User> userIdentifierGenerator = new LongIdentifierGenerator<>();
    private final LongIdentifierGenerator<Post> postIdentifierGenerator = new LongIdentifierGenerator<>();
    private final InMemoryUserTable userTable = new InMemoryUserTable(userIdentifierGenerator);
    private final PostCommentRelationManager postCommentRelationManager = new PostCommentRelationManager();
    private final InMemoryPostTable postTable = new InMemoryPostTable(postIdentifierGenerator, postCommentRelationManager);
    private final InMemoryPostLikeTable postLikeTable = new InMemoryPostLikeTable(null);
    private final UserRepository userRepository = new UserMemoryRepository(userTable);
    private final PostRepository postRepository = new PostMemoryRepository(postTable);
    private final PostLikeRepository postLikeRepository = new PostLikeMemoryRepository(postLikeTable);
    private final UserService userService = new UserService(userRepository);
    private final PostService postService = new PostService(postRepository, postLikeRepository, userService);
    private final User user = User.create("test@test.com", "Test1234!", "testNickname", "");
    private final Post post = Post.create(user, "testTitle", "testContent", "");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        postRepository.save(post);
    }

    @RepeatedTest(value = 100)
    void viewCount_ThreadSafe() throws InterruptedException {
        int loopCount = 100;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                postService.getPost(user.getUserId(), post.getPostId());
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
        assertThat(post.getViewCount()).isEqualTo(expectedSize);
    }

    @RepeatedTest(value = 100)
    void postLike_ThreadSafe() throws InterruptedException {
        int loopCount = 100;

        Runnable runnable = () -> {
            for (int i = 1; i <= loopCount; i++) {
                postService.createOrUpdateLiked(user.getUserId(), post.getPostId());
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

        boolean isLiked = loopCount * numThreads % 2 == 1;
        assertThat(post.getLikeCount()).isEqualTo(isLiked ? 1 : 0);
    }
}