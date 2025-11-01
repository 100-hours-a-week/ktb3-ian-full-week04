package ktb3.full.community.service;

import ktb3.full.community.common.exception.PostNotFoundException;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.PostCreateRequest;
import ktb3.full.community.dto.request.PostUpdateRequest;
import ktb3.full.community.dto.response.PostDetailResponse;
import ktb3.full.community.dto.response.PostResponse;
import ktb3.full.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostLikeService postLikeService;

    public PagedModel<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPages = postRepository.findAll(pageable);
        return new PagedModel<>(postPages.map(PostResponse::from));
    }

    @Transactional
    public PostDetailResponse getPost(long userId, long postId) {
        Post post = getForUpdateOrThrow(postId);

        post.increaseViewCount();

        boolean liked = postLikeService.isLiked(userId, postId);

        return PostDetailResponse.from(post, liked);
    }

    @Transactional
    public PostDetailResponse createPost(long userId, PostCreateRequest request) {
        User user = userService.getOrThrow(userId);
        Post post = request.toEntity(user);

        postRepository.save(post);

        return PostDetailResponse.from(post, false);
    }

    @Transactional
    public PostDetailResponse updatePost(long userId, long postId, PostUpdateRequest request) {
        Post post = getOrThrow(postId);
        userService.validatePermission(userId, post.getUser().getId());

        if (request.getTitle() != null) {
            post.updateTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            post.updateContent(request.getContent());
        }

        if (request.getImage() != null) {
            post.updateImage(request.getImage());
        }

        boolean liked = postLikeService.isLiked(userId, postId);

        return PostDetailResponse.from(post, liked);
    }

    @Transactional
    public void deletePost(long userId, long postId) {
        // soft delete
        Post post = getOrThrow(postId);
        userService.validatePermission(userId, post.getUser().getId());
        post.delete();
    }

    public Post getOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public Post getForUpdateOrThrow(long postId) {
        return postRepository.findByIdForUpdate(postId)
                .orElseThrow(PostNotFoundException::new);
    }
}
