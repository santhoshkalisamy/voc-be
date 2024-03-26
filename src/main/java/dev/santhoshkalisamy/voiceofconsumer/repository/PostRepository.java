package dev.santhoshkalisamy.voiceofconsumer.repository;

import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAll(Sort sort);
}
