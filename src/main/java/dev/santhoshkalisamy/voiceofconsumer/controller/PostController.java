package dev.santhoshkalisamy.voiceofconsumer.controller;

import dev.santhoshkalisamy.voiceofconsumer.entity.GetPostResponse;
import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import dev.santhoshkalisamy.voiceofconsumer.entity.Reaction;
import dev.santhoshkalisamy.voiceofconsumer.entity.ReactionRequest;
import dev.santhoshkalisamy.voiceofconsumer.exception.PostNotFoundException;
import dev.santhoshkalisamy.voiceofconsumer.service.PostService;
import dev.santhoshkalisamy.voiceofconsumer.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    ReactionService reactionService;

    @Autowired
    private PostService postService;

    @PostMapping()
    public Post addPost(@RequestBody Post post, @RequestHeader("Authorization") String token) {
        post.setUserId(token);
        post.setLikeCount(0);
        post.setCommentCount(0);
        return postService.addPost(post);
    }

    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam String query) throws PostNotFoundException {
        return postService.searchPosts(query);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) throws PostNotFoundException {
        return postService.getPost(id);
    }

    @GetMapping()
    public GetPostResponse getAllPosts(@RequestParam int pageSize, @RequestParam int pageNumber) throws PostNotFoundException {
        return postService.getAllPosts(pageSize, pageNumber);
    }

    @PostMapping("/reaction")
    public Reaction reactToPost(@RequestBody ReactionRequest reactionRequest, @RequestHeader("Authorization") String token) throws PostNotFoundException {
        return reactionService.reactToPost(reactionRequest.reactionType(), reactionRequest.postId(), token);
    }

}
