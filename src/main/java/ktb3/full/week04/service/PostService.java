package ktb3.full.week04.service;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.PostCreateRequest;
import ktb3.full.week04.dto.request.PostUpdateRequest;
import ktb3.full.week04.dto.response.PostDetailResponse;
import ktb3.full.week04.dto.response.PostResponse;
import ktb3.full.week04.service.base.Findable;

public interface PostService extends Findable<Post, Long> {

    PageResponse<PostResponse> getAllPosts(PageRequest pageRequest);

    PostDetailResponse getPost(long postId);

    void createPost(long userId, PostCreateRequest request);

    void updatePost(long userId, long postId, PostUpdateRequest request);

    void deletePost(long userId, long postId);

    void createOrUpdateLiked(long userId, long postId);
}
