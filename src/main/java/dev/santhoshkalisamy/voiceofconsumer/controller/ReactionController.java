package dev.santhoshkalisamy.voiceofconsumer.controller;

import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import dev.santhoshkalisamy.voiceofconsumer.entity.Reaction;
import dev.santhoshkalisamy.voiceofconsumer.entity.ReactionRequest;
import dev.santhoshkalisamy.voiceofconsumer.exception.PostNotFoundException;
import dev.santhoshkalisamy.voiceofconsumer.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reaction")
public class ReactionController {

    @Autowired
    ReactionService reactionService;

    @PostMapping()
    public Reaction reactToPost(@RequestBody ReactionRequest reactionRequest, @RequestHeader("Authorization") String token) throws PostNotFoundException {
        return reactionService.reactToPost(reactionRequest.reactionType(), reactionRequest.postId(), token);
    }

    @GetMapping()
    public List<Reaction> getAllReactions(@RequestHeader("Authorization") String token) throws PostNotFoundException {
        return reactionService.getAllReactions(token);
    }

}
