package dev.santhoshkalisamy.voiceofconsumer.entity;

import java.util.List;

public record GetPostResponse(int total, List<Post> posts) {
}
