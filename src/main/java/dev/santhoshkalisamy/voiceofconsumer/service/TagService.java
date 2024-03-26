package dev.santhoshkalisamy.voiceofconsumer.service;

import dev.santhoshkalisamy.voiceofconsumer.entity.TagCount;

import java.util.List;

public interface TagService {
    List<TagCount> findMostPopularTags(int limit);
}
