package ktb3.full.week04.service.impl;

import ktb3.full.week04.common.exception.PostNotFoundException;
import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.PostLike;
import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.PostCreateRequest;
import ktb3.full.week04.dto.request.PostUpdateRequest;
import ktb3.full.week04.dto.response.PostDetailResponse;
import ktb3.full.week04.dto.response.PostLikeRespnose;
import ktb3.full.week04.dto.response.PostResponse;
import ktb3.full.week04.repository.PostLikeRepository;
import ktb3.full.week04.repository.PostRepository;
import ktb3.full.week04.service.PostService;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserService userService;

    @Override
    public PageResponse<PostResponse> getAllPosts(PageRequest pageRequest) {
        PageResponse<Post> posts = postRepository.findAllByLatest(pageRequest);
        List<PostResponse> responses = posts.getContent().stream().map(PostResponse::from).toList();

        return PageResponse.to(posts, responses);
    }

    @Override
    public PostDetailResponse getPost(long userId, long postId) {
        Post post = getOrThrow(postId);
        post.increaseViewCount();
        boolean liked = postLikeRepository.existsAndLiked(userId, postId);

        return PostDetailResponse.from(post, liked);
    }

    @Override
    public PostDetailResponse createPost(long userId, PostCreateRequest request) {
        User user = userService.getOrThrow(userId);
        Post post = request.toEntity(user);

        postRepository.save(post);

        return PostDetailResponse.from(post, false);
    }

    @Override
    public PostDetailResponse updatePost(long userId, long postId, PostUpdateRequest request) {
        Post post = getOrThrow(postId);
        userService.validatePermission(userId, post.getUser().getUserId());

        if (request.getTitle() != null) {
            post.updateTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            post.updateContent(request.getContent());
        }

        if (request.getImage() != null) {
            post.updateImage(request.getImage());
        }

        postRepository.update(post);
        boolean liked = postLikeRepository.existsAndLiked(userId, postId);

        return PostDetailResponse.from(post, liked);
    }

    @Override
    public void deletePost(long userId, long postId) {
        // soft delete
        Post post = getOrThrow(postId);
        userService.validatePermission(userId, post.getUser().getUserId());

        postRepository.update(post);
    }

    @Override
    public PostLikeRespnose createOrUpdateLiked(long userId, long postId) {
        User user = userService.getOrThrow(userId);
        Post post = getOrThrow(postId);
        PostLike postLike = postLikeRepository.findByUserAndPostId(userId, postId)
                .orElse(PostLike.create(user, post));
        postLike.toggle();

        postLikeRepository.saveOrUpdate(postLike);

        return new PostLikeRespnose(postLike.isLiked());
    }

    @Override
    public Post getOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }
}
