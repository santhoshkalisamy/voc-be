package dev.santhoshkalisamy.voiceofconsumer.controller;

import dev.santhoshkalisamy.voiceofconsumer.entity.Profile;
import dev.santhoshkalisamy.voiceofconsumer.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public Profile addProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Profile profile = new Profile();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        profile.setId(jwt.getClaim("sub"));
        profile.setName(jwt.getClaim("name"));
        profile.setEmail(jwt.getClaim("email"));
        profile.setPicture(jwt.getClaim("picture"));
        profile.setLocale(jwt.getClaim("locale"));
        profile.setCreatedAt(new Date());
        return profileService.addProfile(profile);
    }
}
