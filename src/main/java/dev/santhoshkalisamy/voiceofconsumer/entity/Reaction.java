package dev.santhoshkalisamy.voiceofconsumer.entity;

import dev.santhoshkalisamy.voiceofconsumer.enums.ReactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reactions")
public class Reaction {
    @Id
    private String id;
    private String postId;
    private String userId;
    private ReactionType reactionType;

    public Reaction(String postId, String userId, ReactionType reactionType) {
        this.postId = postId;
        this.userId = userId;
        this.reactionType = reactionType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
