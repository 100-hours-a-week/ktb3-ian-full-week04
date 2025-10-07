package ktb3.full.week04.service;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.request.UserAccountUpdateRequest;
import ktb3.full.week04.dto.request.UserLoginRequest;
import ktb3.full.week04.dto.request.UserPasswordUpdateRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.dto.response.UserAccountResponse;
import ktb3.full.week04.service.base.Findable;

public interface UserService extends Findable<User, Long> {

    boolean validateEmailAvailable(String email);

    boolean validateNicknameAvailable(String nickname);

    void register(UserRegisterRequest request);

    LoggedInUser login(UserLoginRequest request);

    UserAccountResponse getUserAccount(Long userId);

    void updateAccount(Long userId, UserAccountUpdateRequest request);

    void updatePassword(Long userId, UserPasswordUpdateRequest request);

    void deleteAccount(Long userId);
}
