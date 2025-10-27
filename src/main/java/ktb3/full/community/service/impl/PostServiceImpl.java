package ktb3.full.community.service.impl;

import ktb3.full.community.common.exception.PostNotFoundException;
import ktb3.full.community.domain.Post;
import ktb3.full.community.domain.PostLike;
import ktb3.full.community.domain.User;
import ktb3.full.community.dto.page.PageRequest;
import ktb3.full.community.dto.page.PageResponse;
import ktb3.full.community.dto.page.Sort;
import ktb3.full.community.dto.request.PostCreateRequest;
import ktb3.full.community.dto.request.PostUpdateRequest;
import ktb3.full.community.dto.response.PostDetailResponse;
import ktb3.full.community.dto.response.PostLikeRespnose;
import ktb3.full.community.dto.response.PostResponse;
import ktb3.full.community.repository.PostLikeRepository;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.service.PostService;
import ktb3.full.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserService userService;

    private final Lock lock = new ReentrantLock();

    @Override
    public PageResponse<PostResponse> getAllPosts(PageRequest pageRequest, Sort sort) {
        PageResponse<Post> posts = postRepository.findAll(pageRequest, sort);
        List<PostResponse> responses = posts.getContent().stream().map(PostResponse::from).toList();

        return PageResponse.to(posts, responses);
    }

    @Override
    public PostDetailResponse getPost(long userId, long postId) {
        Post post;
        lock.lock();
        try {
            post = getOrThrow(postId);
            post.increaseViewCount();
        } finally {
            lock.unlock();
        }

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
        post.delete();

        postRepository.update(post);
    }

    @Override
    public PostLikeRespnose createOrUpdateLiked(long userId, long postId) {
        PostLike postLike;
        User user = userService.getOrThrow(userId);
        Post post = getOrThrow(postId);

        lock.lock();
        try {
            postLike = postLikeRepository.findByUserAndPostId(userId, postId)
                    .orElse(PostLike.create(user, post));
            postLike.toggle();
            postLikeRepository.saveOrUpdate(postLike);
        } finally {
            lock.unlock();
        }

        return new PostLikeRespnose(postLike.isLiked());
    }

    @Override
    public Post getOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }
}
