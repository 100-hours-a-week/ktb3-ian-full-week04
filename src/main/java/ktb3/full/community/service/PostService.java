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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostLikeService postLikeService;

    @Value("${file.path.base}")
    private String fileBasePath;

    @Value("${file.path.image}")
    private String fileImagePath;

    public PagedModel<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPages = postRepository.findAll(pageable);
        return new PagedModel<>(postPages.map(PostResponse::from));
    }

    @Transactional
    public PostDetailResponse getPost(long userId, long postId) {
        postRepository.increaseViewCount(postId);
        Post post = getOrThrow(postId);
        boolean liked = postLikeService.isLiked(userId, postId);
        return PostDetailResponse.from(post, liked);
    }

    @Transactional
    public PostDetailResponse createPost(long userId, PostCreateRequest request) throws IOException {
        MultipartFile image = request.getImage();
        String imagePath = saveImageAndGetPath(request.getImage());
        User user = userService.getOrThrow(userId);
        Post post = request.toEntity(user, imagePath, image.getOriginalFilename());

        postRepository.save(post);

        return PostDetailResponse.from(post, false);
    }

    @Transactional
    public PostDetailResponse updatePost(long userId, long postId, PostUpdateRequest request) throws IOException {
        Post post = getOrThrow(postId);
        userService.validatePermission(userId, post.getUser().getId());

        if (request.getTitle() != null) {
            post.updateTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            post.updateContent(request.getContent());
        }

        if (request.getImage() != null) {
            String imagePath = saveImageAndGetPath(request.getImage());
            post.updateImage(imagePath);
        }

        boolean liked = postLikeService.isLiked(userId, postId);

        return PostDetailResponse.from(post, liked);
    }

    @Transactional
    public void deleteAllPostByUserId(long userId) {
        postRepository.deleteAllByUserId(userId);
    }

    public Post getOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public Post getForUpdateOrThrow(long postId) {
        return postRepository.findByIdForUpdate(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    private String saveImageAndGetPath(MultipartFile image) throws IOException {
        String imagePath = null;

        if (image != null) {
            imagePath = this.fileImagePath + "/" + UUID.randomUUID() + image.getOriginalFilename();
            Path path = Paths.get(this.fileBasePath + imagePath);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
        }

        return imagePath;
    }
}
