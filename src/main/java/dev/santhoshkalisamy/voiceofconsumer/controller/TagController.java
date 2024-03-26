package dev.santhoshkalisamy.voiceofconsumer.controller;

import dev.santhoshkalisamy.voiceofconsumer.entity.TagCount;
import dev.santhoshkalisamy.voiceofconsumer.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/popular")
    public List<TagCount> getPopularTags() {
        return tagService.findMostPopularTags(5);
    }
}
