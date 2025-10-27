package ktb3.full.community.service;

import ktb3.full.community.domain.Post;
import ktb3.full.community.dto.page.PageRequest;
import ktb3.full.community.dto.page.PageResponse;
import ktb3.full.community.dto.page.Sort;
import ktb3.full.community.dto.request.PostCreateRequest;
import ktb3.full.community.dto.request.PostUpdateRequest;
import ktb3.full.community.dto.response.PostDetailResponse;
import ktb3.full.community.dto.response.PostLikeRespnose;
import ktb3.full.community.dto.response.PostResponse;
import ktb3.full.community.service.base.Findable;

public interface PostService extends Findable<Post, Long> {

    PageResponse<PostResponse> getAllPosts(PageRequest pageRequest, Sort sort);

    PostDetailResponse getPost(long userId, long postId);

    PostDetailResponse createPost(long userId, PostCreateRequest request);

    PostDetailResponse updatePost(long userId, long postId, PostUpdateRequest request);

    void deletePost(long userId, long postId);

    PostLikeRespnose createOrUpdateLiked(long userId, long postId);
}
