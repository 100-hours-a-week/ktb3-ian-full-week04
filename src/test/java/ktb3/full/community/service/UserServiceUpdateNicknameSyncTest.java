package ktb3.full.community.service;

import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.UserAccountUpdateRequest;
import ktb3.full.community.repository.jpa.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceUpdateNicknameSyncTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User userA;
    private User userB;
    private User userC;

    @BeforeEach
    void setUp() {
        userA = User.create("testA@test.com", "Test1234!", "testA", "");
        userB = User.create("testB@test.com", "Test1234!", "testB", "");
        userC = User.create("testC@test.com", "Test1234!", "testC", "");
        userRepository.saveAll(List.of(userA, userB, userC));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @RepeatedTest(value = 10)
    void updateNickname_ThreadSafe() throws InterruptedException {
        String newNickname = "newNickname";
        UserAccountUpdateRequest request = new UserAccountUpdateRequest(newNickname, null);

        Thread threadA = new Thread(() -> userService.updateAccount(userA.getId(), request));
        Thread threadB = new Thread(() -> userService.updateAccount(userB.getId(), request));
        Thread threadC = new Thread(() -> userService.updateAccount(userC.getId(), request));

        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        assertThat(userRepository.findAll().stream()
                .filter(user -> user.getNickname().equals(newNickname))
                .count()).isEqualTo(1);
    }
}