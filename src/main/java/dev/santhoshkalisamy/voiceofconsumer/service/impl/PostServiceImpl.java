package dev.santhoshkalisamy.voiceofconsumer.service.impl;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.santhoshkalisamy.voiceofconsumer.entity.GetPostResponse;
import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import dev.santhoshkalisamy.voiceofconsumer.exception.PostNotFoundException;
import dev.santhoshkalisamy.voiceofconsumer.repository.PostRepository;
import dev.santhoshkalisamy.voiceofconsumer.service.PostService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mongodb.client.model.Aggregates.search;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoConverter mongoConverter;

    @Override
    public Post addPost(Post post) {
        Date date = new Date();
        post.setCreatedAt(date);
        post.setUpdatedAt(date);
        return postRepository.save(post);
    }

    @Override
    public Post getPost(String id) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()) {
            throw new PostNotFoundException();
        }
        return post.get();
    }

    @Override
    public GetPostResponse getAllPosts(int pageSize, int pageNumber) throws PostNotFoundException {
        int count = postRepository.findAll().size();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pages = postRepository.findAll(pageable);
        return new GetPostResponse(count, pages.getContent());
    }

    @Override
    public List<Post> searchPosts(String query) {
        List<Post> posts = new ArrayList<>();
        MongoDatabase database = mongoTemplate.getDb();
        MongoCollection<Document> collection = database.getCollection("posts");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("index", "default")
                                .append("wildcard",
                                        new Document("query", "*"+query+"*")
                                                .append("path", Arrays.asList("tags", "content"))
                                                .append("allowAnalyzedField", true))),
                new Document("$sort",
                        new Document("content", 1L)),
                new Document("$limit", 10L)));

        for (Document document : result) {
            posts.add(mongoConverter.read(Post.class, document));
        }
        return posts;
    }
}
