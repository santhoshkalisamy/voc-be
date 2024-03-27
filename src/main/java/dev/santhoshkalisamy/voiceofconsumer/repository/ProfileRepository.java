package dev.santhoshkalisamy.voiceofconsumer.repository;

import dev.santhoshkalisamy.voiceofconsumer.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String>{
}
