package dev.santhoshkalisamy.voiceofconsumer.service.impl;

import dev.santhoshkalisamy.voiceofconsumer.entity.Comment;
import dev.santhoshkalisamy.voiceofconsumer.entity.CommentDTO;
import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import dev.santhoshkalisamy.voiceofconsumer.repository.CommentRepository;
import dev.santhoshkalisamy.voiceofconsumer.repository.PostRepository;
import dev.santhoshkalisamy.voiceofconsumer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment addComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        int count = commentRepository.countByPostId(comment.getPostId());
        Post post = postRepository.findById(comment.getPostId()).get();
        post.setCommentCount(count);
        postRepository.save(post);
        return savedComment;
    }

    @Override
    public List<CommentDTO> getComments(String postId) {
        UnwindOperation unwindOperation = Aggregation.unwind("profile");
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("postId").is(postId)),
                lookup("profiles","userId","_id","profile"),
                unwindOperation
        ).withOptions(AggregationOptions.builder().allowDiskUse(Boolean.TRUE).build());

        return mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Comment.class), CommentDTO.class).getMappedResults();
    }
}
