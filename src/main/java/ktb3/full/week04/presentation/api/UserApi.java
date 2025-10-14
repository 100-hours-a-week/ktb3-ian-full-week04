package ktb3.full.week04.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.week04.common.annotation.constraint.EmailPattern;
import ktb3.full.week04.common.annotation.constraint.NicknamePattern;
import ktb3.full.week04.dto.request.UserLoginRequest;
import ktb3.full.week04.dto.request.UserRegisterRequest;
import ktb3.full.week04.dto.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User", description = "회원 API")
public interface UserApi {

    @Operation(summary = "이메일 중복 검사", description = "이메일이 중복되는지 검사합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<ApiResponse<UserValidationResponse>> validateEmailAvailable(
            @EmailPattern @RequestParam("email") @Parameter(description = "이메일") String email);


    @Operation(summary = "닉네임 중복 검사", description = "닉네임이 중복되는지 검사합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<ApiResponse<UserValidationResponse>> validateNicknameAvailable(
            @NicknamePattern @RequestParam("nickname") @Parameter(description = "닉네임") String nickname);

    @Operation(summary = "회원가입", description = "새로운 회원을 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공")
    })
    ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody UserRegisterRequest userRegisterRequest);

    @Operation(summary = "로그인", description = "회원의 이메일과 비밀번호를 인증합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    ResponseEntity<ApiResponse<UserAccountResponse>> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpSession session);

    @Operation(summary = "회원 조회", description = "ID를 이용해 특정 회원을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(
            @Positive @PathVariable("userId") @Parameter(description = "회원 ID") long userId);
}
