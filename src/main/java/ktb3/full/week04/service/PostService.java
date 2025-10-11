package ktb3.full.week04.service;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.PostCreateRequest;
import ktb3.full.week04.dto.request.PostUpdateRequest;
import ktb3.full.week04.dto.response.PostDetailResponse;
import ktb3.full.week04.dto.response.PostLikeRespnose;
import ktb3.full.week04.dto.response.PostResponse;
import ktb3.full.week04.service.base.Findable;

public interface PostService extends Findable<Post, Long> {

    PageResponse<PostResponse> getAllPosts(PageRequest pageRequest);

    PostDetailResponse getPost(long userId, long postId);

    PostDetailResponse createPost(long userId, PostCreateRequest request);

    PostDetailResponse updatePost(long userId, long postId, PostUpdateRequest request);

    void deletePost(long userId, long postId);

    PostLikeRespnose createOrUpdateLiked(long userId, long postId);
}
