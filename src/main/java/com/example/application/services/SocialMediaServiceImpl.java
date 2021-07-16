package com.example.application.services;

import com.example.application.models.SocialMedia;
import com.example.application.repositories.SocialMediaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {

    private final SocialMediaRepository socialMediaRepository;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository) {
        this.socialMediaRepository = socialMediaRepository;
    }

    @Override
    public Set<SocialMedia> getSocialMediaList(Long contactID) {
        Set<SocialMedia> socialMediaSet = new HashSet<>();
        socialMediaRepository.findSocialMediaByContactId(contactID).iterator().forEachRemaining(socialMediaSet::add);
        return socialMediaSet;
    }

    @Override
    public SocialMedia getSocialMedia(Long socialMediaID) {
        return socialMediaRepository.findSocialMediaById(socialMediaID);
    }

    @Override
    public SocialMedia save(SocialMedia socialMedia) {
        return socialMediaRepository.save(socialMedia);
    }

    @Override
    public SocialMedia update(SocialMedia socialMedia, String socialMediaType, String socialMediaLink) {
        socialMedia.setType(socialMediaType);
        socialMedia.setLink(socialMediaLink);
        return socialMediaRepository.save(socialMedia);
    }

    @Override
    public void delete(SocialMedia socialMedia) {
        socialMediaRepository.delete(socialMedia);
    }

    @Override
    public void deleteSocialMedias(Set<SocialMedia> socialMediaSet) {
        socialMediaRepository.deleteAll(socialMediaSet);
    }
}
