package ktb3.full.community.service;

import ktb3.full.community.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public boolean isLiked(Long userId, Long postId) {
        return postLikeRepository.existsAndLiked(userId, postId)
                .orElse(false);
    }
}
