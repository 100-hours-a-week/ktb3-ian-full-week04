package ktb3.full.community.service;

import ktb3.full.community.common.exception.*;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.UserAccountUpdateRequest;
import ktb3.full.community.dto.request.UserLoginRequest;
import ktb3.full.community.dto.request.UserPasswordUpdateRequest;
import ktb3.full.community.dto.request.UserRegisterRequest;
import ktb3.full.community.dto.response.UserAccountResponse;
import ktb3.full.community.dto.response.UserProfileResponse;
import ktb3.full.community.dto.response.UserValidationResponse;
import ktb3.full.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    private final Lock lock = new ReentrantLock();

    public UserValidationResponse validateEmailAvailable(String email) {
        return new UserValidationResponse(!userRepository.existsByEmail(email));
    }

    public UserValidationResponse validateNicknameAvailable(String nickname) {
        return new UserValidationResponse(!userRepository.existsByNickname(nickname));
    }

    @Transactional
    public long register(UserRegisterRequest request) {
        validateEmailDuplication(request.getEmail());
        validateNicknameDuplication(request.getNickname());
        return userRepository.save(request.toEntity()).getId();
    }

    public UserAccountResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return UserAccountResponse.from(user);
    }

    public UserAccountResponse getUserAccount(long userId) {
        User user = getOrThrow(userId);
        return UserAccountResponse.from(user);
    }

    public UserProfileResponse getUserProfile(long userId) {
        User user = getOrThrow(userId);
        return UserProfileResponse.from(user);
    }

    @Transactional
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

        return UserAccountResponse.from(user);
    }

    @Transactional
    public void updatePassword(long userId, UserPasswordUpdateRequest request) {
        User user = getOrThrow(userId);
        user.updatePassword(request.getPassword());
    }

    @Transactional
    public void deleteAccount(long userId) {
        // soft delete
        User user = getOrThrow(userId);
        user.delete();
    }

    public void validatePermission(long requestUserId, long actualUserId) {
        if (requestUserId != actualUserId) {
            throw new NoPermissionException();
        }
    }

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
