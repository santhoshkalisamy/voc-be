package dev.santhoshkalisamy.voiceofconsumer.entity;

import dev.santhoshkalisamy.voiceofconsumer.enums.ReactionType;

public record ReactionRequest(ReactionType reactionType, String postId) {
}
