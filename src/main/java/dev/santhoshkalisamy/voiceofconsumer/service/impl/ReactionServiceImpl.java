package dev.santhoshkalisamy.voiceofconsumer.service.impl;

import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import dev.santhoshkalisamy.voiceofconsumer.entity.Reaction;
import dev.santhoshkalisamy.voiceofconsumer.enums.ReactionType;
import dev.santhoshkalisamy.voiceofconsumer.exception.PostNotFoundException;
import dev.santhoshkalisamy.voiceofconsumer.repository.PostRepository;
import dev.santhoshkalisamy.voiceofconsumer.repository.ReactionRepository;
import dev.santhoshkalisamy.voiceofconsumer.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Reaction reactToPost(ReactionType reactionType, String postId, String userId) throws PostNotFoundException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isEmpty()) {
            throw new PostNotFoundException();
        }
        Reaction reaction = reactionRepository.findByPostIdAndUserId(postId, userId);
        if(reaction == null) {
            reaction = new Reaction(postId, userId, reactionType);
        }
        reaction.setReactionType(reactionType);
        reactionRepository.save(reaction);
        int count = reactionRepository.countByPostId(postId);
        updatePostReactionCount(optionalPost, count);
        return reaction;
    }

    @Override
    public List<Reaction> getAllReactions(String userId) {
        return reactionRepository.findByUserId(userId);
    }

    private void updatePostReactionCount(Optional<Post> optionalPost, int count) {
        Post post = optionalPost.get();
        post.setLikeCount(count);
        postRepository.save(post);
    }
}
