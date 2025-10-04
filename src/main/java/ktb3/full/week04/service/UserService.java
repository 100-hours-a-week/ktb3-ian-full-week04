package ktb3.full.week04.service;

import ktb3.full.week04.dto.request.UserSignUpRequest;

public interface UserService {

    boolean validateEmail(String email);

    boolean validateNickname(String nickname);

    void signUp(UserSignUpRequest userSignUpRequest);
}
