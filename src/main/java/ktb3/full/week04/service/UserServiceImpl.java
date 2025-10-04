package ktb3.full.week04.service;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.request.UserSignUpRequest;
import ktb3.full.week04.exception.DuplicatedEmailException;
import ktb3.full.week04.exception.DuplicatedNicknameException;
import ktb3.full.week04.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean validateEmail(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public boolean validateNickname(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    @Override
    public void signUp(UserSignUpRequest userSignUpRequest) {
        if (userRepository.existsByEmail(userSignUpRequest.getEmail())) {
            throw new DuplicatedEmailException();
        }

        if (userRepository.existsByNickname(userSignUpRequest.getNickname())) {
            throw new DuplicatedNicknameException();
        }

        User user = userSignUpRequest.toEntity();
        long userId = userRepository.save(user);
        log.debug("회원 가입이 완료되었습니다. userId={}", userId);
    }
}

