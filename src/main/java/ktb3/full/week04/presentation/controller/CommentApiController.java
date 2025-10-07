package ktb3.full.week04.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.week04.common.annotation.resolver.Authentication;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.CommentCreateRequest;
import ktb3.full.week04.dto.request.CommentUpdateRequest;
import ktb3.full.week04.dto.response.ApiResponse;
import ktb3.full.week04.dto.response.CommentResponse;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<PageResponse<CommentResponse>>> getAllComments(
            @Valid PageRequest pageRequest,
            @Positive @PathVariable("postId") long postId) {
        PageResponse<CommentResponse> response = commentService.getAllComments(postId, pageRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.of(response));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Void>> createComment(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId,
            @Valid @RequestBody CommentCreateRequest request) {
        commentService.createComment(loggedInUser.getUserId(), postId, request);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("commentId") long commentId,
            @Valid @RequestBody CommentUpdateRequest request) {
        commentService.updateComment(loggedInUser.getUserId(), commentId, request);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("commentId") long commentId) {
        commentService.deleteComment(loggedInUser.getUserId(), commentId);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }
}
