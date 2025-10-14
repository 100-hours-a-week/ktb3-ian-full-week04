package ktb3.full.week04.presentation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.week04.common.annotation.resolver.Authentication;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.CommentCreateRequest;
import ktb3.full.week04.dto.request.CommentUpdateRequest;
import ktb3.full.week04.dto.response.*;
import ktb3.full.week04.dto.session.LoggedInUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Comment", description = "댓글 API")
public interface CommentApi {

    @Operation(summary = "댓글 목록 조회", description = "Page Number, Size 및 게시글 ID를 이용해 댓글 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<ApiResponse<PageResponse<CommentResponse>>> getAllComments(
            @Valid PageRequest pageRequest,
            @Positive @PathVariable("postId") long postId);

    @Operation(summary = "댓글 상세 조회", description = "ID를 이용해 특정 댓글을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공")
    })
    ResponseEntity<ApiResponse<CommentResponse>> getComment(@Positive @PathVariable("commentId") long commentId);

    @Operation(summary = "댓글 생성", description = "새로운 댓글을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "생성 성공")
    })
    ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId,
            @Valid @RequestBody CommentCreateRequest request);

    @Operation(summary = "댓글 수정", description = "ID를 이용해 댓글을 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공")
    })
    ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("commentId") long commentId,
            @Valid @RequestBody CommentUpdateRequest request);

    @Operation(summary = "댓글 삭제", description = "ID를 이용해 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공")
    })
    ResponseEntity<ApiResponse<Void>> deleteComment(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("commentId") long commentId);
}
