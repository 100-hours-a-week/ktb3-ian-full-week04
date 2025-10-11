package ktb3.full.week04.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.week04.common.annotation.resolver.Authentication;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.PostCreateRequest;
import ktb3.full.week04.dto.request.PostUpdateRequest;
import ktb3.full.week04.dto.response.ApiResponse;
import ktb3.full.week04.dto.response.PostDetailResponse;
import ktb3.full.week04.dto.response.PostResponse;
import ktb3.full.week04.dto.session.LoggedInUser;
import ktb3.full.week04.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostApiController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getAllPosts(@Valid PageRequest pageRequest) {
        PageResponse<PostResponse> response = postService.getAllPosts(pageRequest);
        return ResponseEntity.ok()
                .body(ApiResponse.of(response));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPostDetail(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId) {
        PostDetailResponse response = postService.getPost(loggedInUser.getUserId(), postId);
        return ResponseEntity.ok()
                .body(ApiResponse.of(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostDetailResponse>> createPost(
            @Authentication LoggedInUser loggedInUser,
            @Valid @RequestBody PostCreateRequest request) {
        PostDetailResponse response = postService.createPost(loggedInUser.getUserId(), request);
        return ResponseEntity.created(URI.create(String.format("/posts/%d", response.getPostId())))
                .body(ApiResponse.of(response));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId,
            @Valid @RequestBody PostUpdateRequest request) {
        postService.updatePost(loggedInUser.getUserId(), postId, request);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId) {
        postService.deletePost(loggedInUser.getUserId(), postId);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }

    @PatchMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<Void>> likePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId) {
        postService.createOrUpdateLiked(loggedInUser.getUserId(), postId);
        return ResponseEntity.ok()
                .body(ApiResponse.getBaseResponse());
    }
}
