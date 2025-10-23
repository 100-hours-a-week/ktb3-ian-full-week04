package ktb3.full.week04.service.impl;

import ktb3.full.week04.common.exception.*;
import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.request.UserAccountUpdateRequest;
import ktb3.full.week04.dto.request.UserLoginRequest;
import ktb3.full.week04.dto.request.UserPasswordUpdateRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.dto.response.UserProfileResponse;
import ktb3.full.week04.dto.response.UserValidationResponse;
import ktb3.full.week04.dto.response.UserAccountResponse;
import ktb3.full.week04.repository.UserRepository;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Lock lock = new ReentrantLock();

    @Override
    public UserValidationResponse validateEmailAvailable(String email) {
        return new UserValidationResponse(!userRepository.existsByEmail(email));
    }

    @Override
    public UserValidationResponse validateNicknameAvailable(String nickname) {
        return new UserValidationResponse(!userRepository.existsByNickname(nickname));
    }

    @Override
    public long register(UserRegisterRequest request) {
        validateEmailDuplication(request.getEmail());
        validateNicknameDuplication(request.getNickname());
        return userRepository.save(request.toEntity());
    }

    @Override
    public UserAccountResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return UserAccountResponse.from(user);
    }

    @Override
    public UserAccountResponse getUserAccount(long userId) {
        User user = getOrThrow(userId);
        return UserAccountResponse.from(user);
    }

    @Override
    public UserProfileResponse getUserProfile(long userId) {
        User user = getOrThrow(userId);
        return UserProfileResponse.from(user);
    }

    @Override
    public UserAccountResponse updateAccount(long userId, UserAccountUpdateRequest request) {
        User user = getOrThrow(userId);

        if (request.getNickname() != null) {
            lock.lock();
            try {
                validateNicknameDuplication(request.getNickname());
                user.updateNickname(request.getNickname());
            } finally {
                lock.unlock();
            }
        }

        if (request.getProfileImage() != null) {
            user.updateProfileImage(request.getProfileImage());
        }

        userRepository.update(user);

        return UserAccountResponse.from(user);
    }

    @Override
    public void updatePassword(long userId, UserPasswordUpdateRequest request) {
        User user = getOrThrow(userId);
        user.updatePassword(request.getPassword());

        userRepository.update(user);
    }

    @Override
    public void deleteAccount(long userId) {
        // soft delete
        User user = getOrThrow(userId);
        user.delete();

        userRepository.update(user);
    }

    @Override
    public void validatePermission(long requestUserId, long actualUserId) {
        if (requestUserId != actualUserId) {
            throw new NoPermissionException();
        }
    }

    @Override
    public User getOrThrow(Long userId) {
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
