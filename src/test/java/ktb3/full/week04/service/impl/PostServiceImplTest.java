package ktb3.full.week04.service.impl;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.PostLike;
import ktb3.full.week04.domain.User;
import ktb3.full.week04.infrastructure.database.table.AuditingTable;
import ktb3.full.week04.infrastructure.database.table.Table;
import ktb3.full.week04.repository.PostLikeRepository;
import ktb3.full.week04.repository.PostRepository;
import ktb3.full.week04.repository.UserRepository;
import ktb3.full.week04.infrastructure.database.identifier.LongIdentifierGenerator;
import ktb3.full.week04.repository.impl.PostLikeMemoryRepository;
import ktb3.full.week04.repository.impl.PostMemoryRepository;
import ktb3.full.week04.repository.impl.UserMemoryRepository;
import ktb3.full.week04.service.PostService;
import ktb3.full.week04.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceImplTest {

    private final LongIdentifierGenerator<User> userIdentifierGenerator = new LongIdentifierGenerator<>();
    private final LongIdentifierGenerator<Post> postIdentifierGenerator = new LongIdentifierGenerator<>();
    private final AuditingTable<User, Long> userTable = new AuditingTable<>(userIdentifierGenerator);
    private final AuditingTable<Post, Long> postTable = new AuditingTable<>(postIdentifierGenerator);
    private final Table<PostLike, PostLikeMemoryRepository.UserAndPostId> postLikeTable = new Table<>(null);
    private final UserRepository userRepository = new UserMemoryRepository(userTable);
    private final PostRepository postRepository = new PostMemoryRepository(postTable);
    private final PostLikeRepository postLikeRepository = new PostLikeMemoryRepository(postLikeTable);
    private final UserService userService = new UserServiceImpl(userRepository);
    private final PostService postService = new PostServiceImpl(postRepository, postLikeRepository, userService);
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