package ktb3.full.week04.presentation.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ktb3.full.week04.common.annotation.resolver.Authentication;
import ktb3.full.week04.dto.request.UserAccountUpdateRequest;
import ktb3.full.week04.dto.request.UserPasswordUpdateRequest;
import ktb3.full.week04.dto.response.ApiResponse;
import ktb3.full.week04.dto.response.UserAccountResponse;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.presentation.api.AuthenticatedUserApi;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class AuthenticatedUserApiController implements AuthenticatedUserApi {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserAccountResponse>> getUserAccount(@Authentication LoggedInUser loggedInUser) {
        UserAccountResponse userAccountResponse = userService.getUserAccount(loggedInUser.getUserId());
        return ResponseEntity.ok()
                .body(ApiResponse.of(userAccountResponse));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<UserAccountResponse>> updateUserAccount(
            @Authentication LoggedInUser loggedInUser,
            @Valid @RequestBody UserAccountUpdateRequest userAccountUpdateRequest) {
        UserAccountResponse userAccountResponse = userService.updateAccount(loggedInUser.getUserId(), userAccountUpdateRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.of(userAccountResponse));
    }

    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @Authentication LoggedInUser loggedInUser,
            @Valid @RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest) {
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
    public ResponseEntity<ApiResponse<Void>> deleteUserAccount(@Authentication LoggedInUser loggedInUser, HttpSession session) {
        userService.deleteAccount(loggedInUser.getUserId());
        session.invalidate();
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }
}
