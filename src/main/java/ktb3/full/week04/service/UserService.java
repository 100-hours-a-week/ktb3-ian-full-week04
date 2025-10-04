package ktb3.full.week04.service;

import ktb3.full.week04.dto.request.UserAccountUpdateRequest;
import ktb3.full.week04.dto.request.UserLoginRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.dto.response.UserAccountResponse;

public interface UserService {

    boolean validateEmailDuplication(String email);

    boolean validateNicknameDuplication(String nickname);

    void register(UserRegisterRequest request);

    void login(UserLoginRequest request);

    UserAccountResponse getUserAccount(Long userId);

    void updateAccount(Long userId, UserAccountUpdateRequest request);

    void deleteAccount(Long userId);
}
