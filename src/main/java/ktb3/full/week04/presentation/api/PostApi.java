package ktb3.full.week04.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.week04.common.annotation.resolver.Authentication;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.PostCreateRequest;
import ktb3.full.week04.dto.request.PostUpdateRequest;
import ktb3.full.week04.dto.response.*;
import ktb3.full.week04.dto.session.LoggedInUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Post", description = "게시글 API")
public interface PostApi {

    @Operation(summary = "게시글 목록 조회", description = "Page Number, Size로 게시글 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<ApiSuccessResponse<PageResponse<PostResponse>>> getAllPosts(@Valid PageRequest pageRequest);

    @Operation(summary = "게시글 상세 조회", description = "ID를 이용해 특정 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<ApiSuccessResponse<PostDetailResponse>> getPostDetail(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") @Parameter(description = "게시글 ID") long postId);

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공")
    })
    ResponseEntity<ApiSuccessResponse<PostDetailResponse>> createPost(
            @Authentication LoggedInUser loggedInUser,
            @Valid @RequestBody PostCreateRequest request);

    @Operation(summary = "게시글 수정", description = "ID를 이용해  게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    ResponseEntity<ApiSuccessResponse<PostDetailResponse>> updatePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") @Parameter(description = "게시글 ID") long postId,
            @Valid @RequestBody PostUpdateRequest request);

    @Operation(summary = "게시글 삭제", description = "ID를 이용해 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공")
    })
    ResponseEntity<ApiSuccessResponse<Void>> deletePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") @Parameter(description = "게시글 ID") long postId);

    @Operation(summary = "좋아요", description = "좋아요를 누르거나 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    ResponseEntity<ApiSuccessResponse<PostLikeRespnose>> likePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") @Parameter(description = "게시글 ID") long postId);
}
