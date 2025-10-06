package ktb3.full.week04.service.impl;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.request.UserAccountUpdateRequest;
import ktb3.full.week04.dto.request.UserLoginRequest;
import ktb3.full.week04.dto.request.UserPasswordUpdateRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.dto.response.UserAccountResponse;
import ktb3.full.week04.common.exception.DuplicatedEmailException;
import ktb3.full.week04.common.exception.DuplicatedNicknameException;
import ktb3.full.week04.common.exception.InvalidCredentialsException;
import ktb3.full.week04.common.exception.UserNotFoundException;
import ktb3.full.week04.repository.UserRepository;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean validateEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public boolean validateNicknameAvailable(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    @Override
    public void register(UserRegisterRequest request) {
        validateEmailDuplication(request.getEmail());
        validateNicknameDuplication(request.getNickname());

        userRepository.save(request.toEntity());
    }

    @Override
    public LoggedInUser login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return LoggedInUser.from(user);
    }

    @Override
    public UserAccountResponse getUserAccount(Long userId) {
        User user = getUserOrThrow(userId);
        return UserAccountResponse.from(user);
    }

    @Override
    public void updateAccount(Long userId, UserAccountUpdateRequest request) {
        User user = getUserOrThrow(userId);
        user.updateProfileImage(request.getProfileImage());

        if (request.getNickname() != null) {
            validateNicknameDuplication(request.getNickname());
            user.updateNickname(request.getNickname());
        }

        userRepository.update(user);
    }

    @Override
    public void updatePassword(Long userId, UserPasswordUpdateRequest request) {
        User user = getUserOrThrow(userId);
        user.updatePassword(request.getPassword());

        userRepository.update(user);
    }

    @Override
    public void deleteAccount(Long userId) {
        // soft delete
        User user = getUserOrThrow(userId);
        user.delete();

        userRepository.update(user);
    }

    public User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private void validateEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    private void validateNicknameDuplication(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicatedNicknameException();
        }
    }
}
