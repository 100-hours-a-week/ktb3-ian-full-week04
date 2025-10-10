package ktb3.full.week04.service;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.request.UserAccountUpdateRequest;
import ktb3.full.week04.dto.request.UserLoginRequest;
import ktb3.full.week04.dto.request.UserPasswordUpdateRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.dto.response.UserProfileResponse;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.dto.response.UserAccountResponse;
import ktb3.full.week04.service.base.Findable;

public interface UserService extends Findable<User, Long> {

    boolean validateEmailAvailable(String email);

    boolean validateNicknameAvailable(String nickname);

    long register(UserRegisterRequest request);

    LoggedInUser login(UserLoginRequest request);

    UserAccountResponse getUserAccount(long userId);

    UserProfileResponse getUserProfile(long userId);

    void updateAccount(long userId, UserAccountUpdateRequest request);

    void updatePassword(long userId, UserPasswordUpdateRequest request);

    void deleteAccount(long userId);

    void validatePermission(long requestUserId, long actualUserId);
}
