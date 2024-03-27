package dev.santhoshkalisamy.voiceofconsumer.controller;

import dev.santhoshkalisamy.voiceofconsumer.entity.*;
import dev.santhoshkalisamy.voiceofconsumer.exception.PostNotFoundException;
import dev.santhoshkalisamy.voiceofconsumer.service.CommentService;
import dev.santhoshkalisamy.voiceofconsumer.service.PostService;
import dev.santhoshkalisamy.voiceofconsumer.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    ReactionService reactionService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @PostMapping()
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        post.setUserId(authentication.getName());
        post.setUserName(((Jwt)authentication.getPrincipal()).getClaims().get("name").toString());
        post.setLikeCount(0);
        post.setCommentCount(0);
        return ResponseEntity.status(201).body(postService.addPost(post));
    }

    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam String query) throws PostNotFoundException {
        return postService.searchPosts(query);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) throws PostNotFoundException {
        return postService.getPost(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) throws PostNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        return postService.deletePost(id, authentication.getName());
    }

    @GetMapping("/all")
    public GetPostResponse getAllPosts(@RequestParam int pageSize,
                                       @RequestParam int pageNumber,
                                       @RequestParam String search,
                                       @RequestParam List<String> categories,
                                       @RequestParam List<String> tags
                                       ) throws PostNotFoundException {
        return postService.getAllPosts(pageSize, pageNumber, search, categories, tags);
    }

    @GetMapping()
    public GetPostResponse getAllPosts(@RequestParam int pageSize,
                                       @RequestParam int pageNumber
    ) throws PostNotFoundException {
        return postService.getAllPosts(pageSize, pageNumber);
    }

    @PostMapping("/reaction")
    public ResponseEntity<Reaction> reactToPost(@RequestBody ReactionRequest reactionRequest) throws PostNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(201).body(reactionService.reactToPost(reactionRequest.reactionType(), reactionRequest.postId(), authentication.getName()));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable String id, @RequestBody Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        comment.setPostId(id);
        comment.setUserId(authentication.getName());
        return ResponseEntity.status(201).body(commentService.addComment(comment));
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable String id) {
        return ResponseEntity.status(201).body(commentService.getComments(id));
    }

}
