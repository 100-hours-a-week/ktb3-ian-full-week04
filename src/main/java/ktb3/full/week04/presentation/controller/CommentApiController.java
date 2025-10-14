package ktb3.full.week04.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.week04.common.annotation.resolver.Authentication;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.CommentCreateRequest;
import ktb3.full.week04.dto.request.CommentUpdateRequest;
import ktb3.full.week04.dto.response.ApiSuccessResponse;
import ktb3.full.week04.dto.response.CommentResponse;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.presentation.api.CommentApi;
import ktb3.full.week04.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentApiController implements CommentApi {

    private final CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiSuccessResponse<PageResponse<CommentResponse>>> getAllComments(
            @Valid PageRequest pageRequest,
            @Positive @PathVariable("postId") long postId) {
        PageResponse<CommentResponse> response = commentService.getAllComments(postId, pageRequest);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ApiSuccessResponse<CommentResponse>> getComment(@Positive @PathVariable("commentId") long commentId) {
        CommentResponse response = commentService.getComment(commentId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiSuccessResponse<CommentResponse>> createComment(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId,
            @Valid @RequestBody CommentCreateRequest request) {
        CommentResponse response = commentService.createComment(loggedInUser.getUserId(), postId, request);
        return ResponseEntity.created(URI.create(String.format("/comments/%d", response.getCommentId())))
                .body(ApiSuccessResponse.of(response));
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ApiSuccessResponse<CommentResponse>> updateComment(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("commentId") long commentId,
            @Valid @RequestBody CommentUpdateRequest request) {
        CommentResponse response = commentService.updateComment(loggedInUser.getUserId(), commentId, request);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteComment(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("commentId") long commentId) {
        commentService.deleteComment(loggedInUser.getUserId(), commentId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }
}
