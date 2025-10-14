package ktb3.full.week04.presentation.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.week04.common.annotation.constraint.EmailPattern;
import ktb3.full.week04.common.annotation.constraint.NicknamePattern;
import ktb3.full.week04.dto.request.UserLoginRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.dto.response.ApiSuccessResponse;
import ktb3.full.week04.dto.response.UserAccountResponse;
import ktb3.full.week04.dto.response.UserProfileResponse;
import ktb3.full.week04.dto.response.UserValidationResponse;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.presentation.api.UserApi;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static ktb3.full.week04.common.Constants.SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER;

@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserApiController implements UserApi {

    private final UserService userService;

    @GetMapping("/email-validation")
    public ResponseEntity<ApiSuccessResponse<UserValidationResponse>> validateEmailAvailable(@EmailPattern @RequestParam("email") String email) {
        UserValidationResponse userValidationResponse = userService.validateEmailAvailable(email);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userValidationResponse));
    }

    @GetMapping("/nickname-validation")
    public ResponseEntity<ApiSuccessResponse<UserValidationResponse>> validateNicknameAvailable(@NicknamePattern @RequestParam("nickname") String nickname) {
        UserValidationResponse userValidationResponse = userService.validateNicknameAvailable(nickname);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userValidationResponse));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<Void>> signUp(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        long userId = userService.register(userRegisterRequest);
        return ResponseEntity.created(URI.create(String.format("/users/%d", userId)))
                .body(ApiSuccessResponse.getBaseResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<UserAccountResponse>> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        UserAccountResponse userAccountResponse = userService.login(userLoginRequest);
        session.setAttribute(SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER, new LoggedInUser(userAccountResponse.getUserId()));
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userAccountResponse));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiSuccessResponse<UserProfileResponse>> getUserProfile(@Positive @PathVariable("userId") long userId) {
        UserProfileResponse userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userProfile));
    }
}
