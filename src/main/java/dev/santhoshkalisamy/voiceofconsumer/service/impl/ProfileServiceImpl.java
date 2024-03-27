package dev.santhoshkalisamy.voiceofconsumer.service.impl;

import dev.santhoshkalisamy.voiceofconsumer.entity.Profile;
import dev.santhoshkalisamy.voiceofconsumer.repository.ProfileRepository;
import dev.santhoshkalisamy.voiceofconsumer.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository  profileRepository;

    @Override
    public Profile addProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
