package com.example.application.services;

import com.example.application.models.SocialMedia;

import java.util.Set;

public interface SocialMediaService {
    Set<SocialMedia> getSocialMediaList(Long contactID);
    SocialMedia getSocialMedia(Long socialMediaID);
    SocialMedia save(SocialMedia socialMedia);
    SocialMedia update(SocialMedia socialMedia, String socialMediaType, String socialMediaLink);
    void delete(SocialMedia socialMedia);
    void deleteSocialMedias(Set<SocialMedia> socialMediaSet);
}
