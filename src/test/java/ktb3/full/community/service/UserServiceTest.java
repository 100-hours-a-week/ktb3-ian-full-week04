package ktb3.full.community.service;

import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.UserRegisterRequest;
import ktb3.full.community.repository.jpa.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

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
}