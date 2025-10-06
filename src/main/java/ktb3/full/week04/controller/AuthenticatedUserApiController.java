package ktb3.full.week04.controller;

import jakarta.servlet.http.HttpSession;
import ktb3.full.week04.dto.request.UserAccountUpdateRequest;
import ktb3.full.week04.dto.request.UserPasswordUpdateRequest;
import ktb3.full.week04.dto.response.ApiResponse;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.dto.response.UserAccountResponse;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ktb3.full.week04.common.Constants.SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class AuthenticatedUserApiController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserAccountResponse>> getUserAccount(
            @SessionAttribute(name = SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER) LoggedInUser loggedInUser) {
        UserAccountResponse userAccountResponse = userService.getUserAccount(loggedInUser.getUserId());
        return ResponseEntity.ok()
                .body(ApiResponse.of(userAccountResponse));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateUserAccount(
            @SessionAttribute(name = SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER) LoggedInUser loggedInUser,
            @RequestBody UserAccountUpdateRequest userAccountUpdateRequest) {
        userService.updateAccount(loggedInUser.getUserId(), userAccountUpdateRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }

    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @SessionAttribute(name = SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER) LoggedInUser loggedInUser,
            @RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest) {
        userService.updatePassword(loggedInUser.getUserId(), userPasswordUpdateRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUserAccount(
            @SessionAttribute(name = SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER) LoggedInUser loggedInUser,
            HttpSession session) {
        userService.deleteAccount(loggedInUser.getUserId());
        session.invalidate();
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }
}
