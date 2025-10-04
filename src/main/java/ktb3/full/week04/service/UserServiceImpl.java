package ktb3.full.week04.service;

import ktb3.full.week04.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

