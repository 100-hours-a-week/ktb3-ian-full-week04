package ktb3.full.community.service;

import ktb3.full.community.common.exception.PostNotFoundException;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.PostLike;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.PostCreateRequest;
import ktb3.full.community.dto.request.PostUpdateRequest;
import ktb3.full.community.dto.response.PostDetailResponse;
import ktb3.full.community.dto.response.PostLikeRespnose;
import ktb3.full.community.dto.response.PostResponse;
import ktb3.full.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeService postLikeService;
    private final UserService userService;

    private final Lock lock = new ReentrantLock();

    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return null;
    }

    public PostDetailResponse getPost(long userId, long postId) {
        Post post;
        lock.lock();
        try {
            post = getOrThrow(postId);
            post.increaseViewCount();
        } finally {
            lock.unlock();
        }

        return PostDetailResponse.from(post, false);
    }

    public PostDetailResponse createPost(long userId, PostCreateRequest request) {
        User user = userService.getOrThrow(userId);
        Post post = request.toEntity(user);

        postRepository.save(post);

        return PostDetailResponse.from(post, false);
    }

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

        return PostDetailResponse.from(post, false);
    }

    public void deletePost(long userId, long postId) {
        // soft delete
        Post post = getOrThrow(postId);
        userService.validatePermission(userId, post.getUser().getId());
        post.delete();
    }

    public PostLikeRespnose createOrUpdateLiked(long userId, long postId) {
        PostLike postLike;
        User user = userService.getOrThrow(userId);
        Post post = getOrThrow(postId);

        lock.lock();
        try {
//            postLike = postLikeRepository.findByUserAndPostId(userId, postId)
//                    .orElse(PostLike.create(user, post));
//            postLike.toggle();
//            postLikeRepository.saveOrUpdate(postLike);
        } finally {
            lock.unlock();
        }

        return new PostLikeRespnose(false);
    }

    public Post getOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }
}
