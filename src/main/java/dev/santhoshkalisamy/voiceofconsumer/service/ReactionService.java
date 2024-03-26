package dev.santhoshkalisamy.voiceofconsumer.service;

import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import dev.santhoshkalisamy.voiceofconsumer.entity.Reaction;
import dev.santhoshkalisamy.voiceofconsumer.enums.ReactionType;
import dev.santhoshkalisamy.voiceofconsumer.exception.PostNotFoundException;

import java.util.List;

public interface ReactionService {
    Reaction reactToPost(ReactionType reactionType, String postId, String userId) throws PostNotFoundException;

    List<Reaction> getAllReactions(String userId);
}
