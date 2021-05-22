package wooteco.prolog.post.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.post.service.PostService;
import wooteco.prolog.post.web.controller.dto.PostRequest;
import wooteco.prolog.post.web.controller.dto.PostResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody List<PostRequest> postRequests) {
        PostResponse postResponse = postService.insertLogs(postRequests);
        return ResponseEntity.created(URI.create("/posts/" + postResponse.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> showAll() {
        List<PostResponse> postResponses = postService.findAll();
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> showPost(@PathVariable Long id) {
        PostResponse postResponse = postService.findById(id);
        return ResponseEntity.ok(postResponse);
    }
}