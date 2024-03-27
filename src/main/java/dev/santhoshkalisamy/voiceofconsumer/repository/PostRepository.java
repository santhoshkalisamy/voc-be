package dev.santhoshkalisamy.voiceofconsumer.repository;

import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAll(Sort sort);

    @Query("{$and: [{'$or': [{'title': {$regex: ?0, $options: 'i'}}, {'content': {$regex: ?0, $options: 'i'}}]}, {'tags': {$in: ?1}}, {'category': {$in: ?2}}]}")
    List<Post> findByFilters(String title, List<String> tags, List<String> category);

    @Query("{$and: [{'$or': [{'title': {$regex: ?0, $options: 'i'}}, {'content': {$regex: ?0, $options: 'i'}}]}, {'category': {$in: ?1}}, {'tags': {$in: ?2}}]}")
    List<Post> findByQuery(String title, List<String> category, List<String> tags);

    @Query("{$and: [{'$or': [{'title': {$regex: ?0, $options: 'i'}}, {'content': {$regex: ?0, $options: 'i'}}]}, {'category': {$in: ?1}}]}")
    List<Post> findByQuery(String title, List<String> category);
}
