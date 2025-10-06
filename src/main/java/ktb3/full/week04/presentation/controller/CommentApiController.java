package ktb3.full.week04.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
@RestController
public class CommentApiController {

}
