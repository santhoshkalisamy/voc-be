package dev.santhoshkalisamy.voiceofconsumer.service.impl;

import dev.santhoshkalisamy.voiceofconsumer.entity.TagCount;
import dev.santhoshkalisamy.voiceofconsumer.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<TagCount> findMostPopularTags(int limit) {
        Aggregation aggregation = newAggregation(
                unwind("tags"),
                group("tags").count().as("count"),
                project("count").and("tag").previousOperation(),
                sort(Sort.Direction.DESC, "count"),
                limit(limit)
        );

        AggregationResults<TagCount> results = mongoTemplate.aggregate(aggregation, "posts", TagCount.class);
        return results.getMappedResults();
    }
}
