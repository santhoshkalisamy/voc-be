package dev.santhoshkalisamy.voiceofconsumer.repository;

import dev.santhoshkalisamy.voiceofconsumer.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByPostId(String postId);

    int countByPostId(String postId);
}
