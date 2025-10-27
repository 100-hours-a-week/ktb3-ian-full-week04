package ktb3.full.community.service;

import ktb3.full.community.domain.User;
import ktb3.full.community.dto.request.UserAccountUpdateRequest;
import ktb3.full.community.dto.request.UserLoginRequest;
import ktb3.full.community.dto.request.UserPasswordUpdateRequest;
import ktb3.full.community.dto.request.UserRegisterRequest;
import ktb3.full.community.dto.response.UserProfileResponse;
import ktb3.full.community.dto.response.UserValidationResponse;
import ktb3.full.community.dto.response.UserAccountResponse;
import ktb3.full.community.service.base.Findable;

public interface UserService extends Findable<User, Long> {

    UserValidationResponse validateEmailAvailable(String email);

    UserValidationResponse validateNicknameAvailable(String nickname);

    long register(UserRegisterRequest request);

    UserAccountResponse login(UserLoginRequest request);

    UserAccountResponse getUserAccount(long userId);

    UserProfileResponse getUserProfile(long userId);

    UserAccountResponse updateAccount(long userId, UserAccountUpdateRequest request);

    void updatePassword(long userId, UserPasswordUpdateRequest request);

    void deleteAccount(long userId);

    void validatePermission(long requestUserId, long actualUserId);
}
