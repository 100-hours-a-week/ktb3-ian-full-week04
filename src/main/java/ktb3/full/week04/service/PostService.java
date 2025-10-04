package ktb3.full.week04.service;

import ktb3.full.week04.dto.page.Page;
import ktb3.full.week04.dto.page.Pageable;
import ktb3.full.week04.dto.request.PostCreateRequest;
import ktb3.full.week04.dto.request.PostUpdateRequest;
import ktb3.full.week04.dto.response.PostDetailResponse;
import ktb3.full.week04.dto.response.PostResponse;

public interface PostService {

    Page<PostResponse> getAllPosts(Pageable pageable);

    PostDetailResponse getPost(Long postId);

    void createPost(PostCreateRequest request);

    void updatePost(Long userId, Long postId, PostUpdateRequest request);

    void deletePost(Long userId, Long postId);

    void createOrUpdateLiked(Long userId, Long postId);
}
