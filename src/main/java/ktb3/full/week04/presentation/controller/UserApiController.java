package ktb3.full.week04.presentation.controller;

import jakarta.servlet.http.HttpSession;
import ktb3.full.week04.dto.request.UserLoginRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.dto.response.ApiResponse;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ktb3.full.week04.global.common.Constants.SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/email-validation")
    public ResponseEntity<ApiResponse<Boolean>> validateEmailAvailable(@RequestParam("email") String email) {
        boolean available = userService.validateEmailAvailable(email);
        return ResponseEntity.ok()
                .body(ApiResponse.of(available));
    }

    @GetMapping("/nickname-validation")
    public ResponseEntity<ApiResponse<Boolean>> validateNicknameAvailable(@RequestParam("nickname") String nickname) {
        boolean available = userService.validateNicknameAvailable(nickname);
        return ResponseEntity.ok()
                .body(ApiResponse.of(available));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody UserRegisterRequest userRegisterRequest) {
        userService.register(userRegisterRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        LoggedInUser loggedInUser = userService.login(userLoginRequest);
        session.setAttribute(SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER, loggedInUser);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }
}
