package dev.santhoshkalisamy.voiceofconsumer.repository;

import dev.santhoshkalisamy.voiceofconsumer.entity.Reaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReactionRepository extends MongoRepository<Reaction, String> {
    public int countByPostId(String postId);

    List<Reaction> findByUserId(String userId);

    Reaction findByPostIdAndUserId(String postId, String userId);
}
