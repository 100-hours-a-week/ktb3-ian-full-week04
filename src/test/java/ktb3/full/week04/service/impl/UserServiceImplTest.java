package ktb3.full.week04.service.impl;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.request.UserAccountUpdateRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.repository.impl.UserMemoryRepository;
import ktb3.full.week04.service.UserService;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceImplTest {

    private final UserMemoryRepository userRepository = new UserMemoryRepository();
    private final UserService userService = new UserServiceImpl(userRepository);

    @RepeatedTest(value = 10)
    void signUp_ThreadSafe() throws InterruptedException {
        String email = "test@test.com";
        String password = "Test1234!";
        String nickname = "test";
        UserRegisterRequest request = new UserRegisterRequest(email, password, nickname, "");

        Runnable runnable = () -> userService.register(request);

        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);
        Thread threadC = new Thread(runnable);

        threadA.start();
        threadB.start();
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @RepeatedTest(value = 10)
    void updateNickname_ThreadSafe() throws InterruptedException {
        String newNickname = "newNickname";
        User userA = User.create("testA@test.com", "Test1234!", "testA", "");
        User userB = User.create("testB@test.com", "Test1234!", "testB", "");
        User userC = User.create("testC@test.com", "Test1234!", "testC", "");
        userRepository.saveAll(List.of(userA, userB, userC));
        UserAccountUpdateRequest request = new UserAccountUpdateRequest(newNickname, null);

        Thread threadA = new Thread(() -> userService.updateAccount(userA.getUserId(), request));
        Thread threadB = new Thread(() -> userService.updateAccount(userB.getUserId(), request));
        Thread threadC = new Thread(() -> userService.updateAccount(userC.getUserId(), request));

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