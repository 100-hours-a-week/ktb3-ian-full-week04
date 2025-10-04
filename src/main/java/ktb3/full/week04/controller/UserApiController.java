package ktb3.full.week04.controller;

import ktb3.full.week04.annotation.constraint.ValidEmail;
import ktb3.full.week04.annotation.constraint.ValidNickname;
import ktb3.full.week04.dto.response.ApiResponse;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/users/email-validation")
    public ResponseEntity<ApiResponse<Boolean>> emailValidation(@RequestParam(name = "email") @ValidEmail String email) {
        boolean result = userService.validateEmail(email);

        return ResponseEntity.ok()
                .body(ApiResponse.of(result));
    }

    @GetMapping("/users/nickname-validation")
    public ResponseEntity<ApiResponse<Boolean>> nicknameValidation(@RequestParam(name = "nickname") @ValidNickname String nickname) {
        boolean result = userService.validateNickname(nickname);

        return ResponseEntity.ok()
                .body(ApiResponse.of(result));
    }
}