package dev.santhoshkalisamy.voiceofconsumer.service;

import dev.santhoshkalisamy.voiceofconsumer.entity.Comment;
import dev.santhoshkalisamy.voiceofconsumer.entity.CommentDTO;

import java.util.List;

public interface CommentService {
    public Comment addComment(Comment comment);
    public List<CommentDTO> getComments(String postId);
}
