package dev.santhoshkalisamy.voiceofconsumer.service;

import dev.santhoshkalisamy.voiceofconsumer.entity.GetPostResponse;
import dev.santhoshkalisamy.voiceofconsumer.entity.Post;
import dev.santhoshkalisamy.voiceofconsumer.exception.PostNotFoundException;

import java.util.List;

public interface PostService {
    Post addPost(Post post);

    Post getPost(String id) throws PostNotFoundException;

    GetPostResponse getAllPosts(int pageSize, int pageNumber) throws PostNotFoundException;

    List<Post> searchPosts(String query);
}
