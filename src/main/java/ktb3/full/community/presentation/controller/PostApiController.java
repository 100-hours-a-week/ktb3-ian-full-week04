package ktb3.full.community.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.community.common.annotation.resolver.Authentication;
import ktb3.full.community.dto.page.PageRequest;
import ktb3.full.community.dto.page.PageResponse;
import ktb3.full.community.dto.page.Sort;
import ktb3.full.community.dto.request.PostCreateRequest;
import ktb3.full.community.dto.request.PostUpdateRequest;
import ktb3.full.community.dto.response.ApiSuccessResponse;
import ktb3.full.community.dto.response.PostDetailResponse;
import ktb3.full.community.dto.response.PostLikeRespnose;
import ktb3.full.community.dto.response.PostResponse;
import ktb3.full.community.dto.session.LoggedInUser;
import ktb3.full.community.presentation.api.PostApi;
import ktb3.full.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostApiController implements PostApi {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<PostResponse>>> getAllPosts(@Valid PageRequest pageRequest, @Valid Sort sort) {
        PageResponse<PostResponse> response = postService.getAllPosts(pageRequest, sort);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<PostDetailResponse>> getPostDetail(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId) {
        PostDetailResponse response = postService.getPost(loggedInUser.getUserId(), postId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<PostDetailResponse>> createPost(
            @Authentication LoggedInUser loggedInUser,
            @Valid @RequestBody PostCreateRequest request) {
        PostDetailResponse response = postService.createPost(loggedInUser.getUserId(), request);
        return ResponseEntity.created(URI.create(String.format("/posts/%d", response.getPostId())))
                .body(ApiSuccessResponse.of(response));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<PostDetailResponse>> updatePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId,
            @Valid @RequestBody PostUpdateRequest request) {
        PostDetailResponse response = postService.updatePost(loggedInUser.getUserId(), postId, request);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<Void>> deletePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId) {
        postService.deletePost(loggedInUser.getUserId(), postId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }

    @PatchMapping("/{postId}/like")
    public ResponseEntity<ApiSuccessResponse<PostLikeRespnose>> likePost(
            @Authentication LoggedInUser loggedInUser,
            @Positive @PathVariable("postId") long postId) {
        PostLikeRespnose postLikeRespnose = postService.createOrUpdateLiked(loggedInUser.getUserId(), postId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(postLikeRespnose));
    }
}
