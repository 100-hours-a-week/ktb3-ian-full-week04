package ktb3.full.community.presentation.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ktb3.full.community.common.annotation.resolver.Authentication;
import ktb3.full.community.dto.request.UserAccountUpdateRequest;
import ktb3.full.community.dto.request.UserPasswordUpdateRequest;
import ktb3.full.community.dto.response.ApiSuccessResponse;
import ktb3.full.community.dto.response.UserAccountResponse;
import ktb3.full.community.dto.session.LoggedInUser;
import ktb3.full.community.presentation.api.AuthenticatedUserApi;
import ktb3.full.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class AuthenticatedUserApiController implements AuthenticatedUserApi {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<UserAccountResponse>> getUserAccount(@Authentication LoggedInUser loggedInUser) {
        UserAccountResponse userAccountResponse = userService.getUserAccount(loggedInUser.getUserId());
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userAccountResponse));
    }

    @PatchMapping
    public ResponseEntity<ApiSuccessResponse<UserAccountResponse>> updateUserAccount(
            @Authentication LoggedInUser loggedInUser,
            @Valid @RequestBody UserAccountUpdateRequest userAccountUpdateRequest) {
        UserAccountResponse userAccountResponse = userService.updateAccount(loggedInUser.getUserId(), userAccountUpdateRequest);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(userAccountResponse));
    }

    @PatchMapping("/password")
    public ResponseEntity<ApiSuccessResponse<Void>> updatePassword(
            @Authentication LoggedInUser loggedInUser,
            @Valid @RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest) {
        userService.updatePassword(loggedInUser.getUserId(), userPasswordUpdateRequest);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse<Void>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }

    @DeleteMapping
    public ResponseEntity<ApiSuccessResponse<Void>> deleteUserAccount(@Authentication LoggedInUser loggedInUser, HttpSession session) {
        userService.deleteAccount(loggedInUser.getUserId());
        session.invalidate();
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }
}
