package ktb3.full.community.service;

import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.UserAccountUpdateRequest;
import ktb3.full.community.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    void updateNickname_ThreadSafe() {
        String newNickname = "newName";
        UserAccountUpdateRequest request = new UserAccountUpdateRequest(newNickname, null);

        int numThread = 3;
        try (ExecutorService executor = Executors.newFixedThreadPool(numThread)) {
            executor.submit(() -> userService.updateAccount(userA.getId(), request));
            executor.submit(() -> userService.updateAccount(userB.getId(), request));
            executor.submit(() -> userService.updateAccount(userC.getId(), request));
        }

        assertThat(userRepository.findAll().stream()
                .filter(user -> newNickname.equals(user.getNickname().trim()))
                .count()).isEqualTo(1);
    }
}