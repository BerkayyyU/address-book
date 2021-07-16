package com.example.application.repositories;

import com.example.application.models.SocialMedia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SocialMediaRepository extends CrudRepository<SocialMedia,Long> {
    SocialMedia findSocialMediaById(Long socialMediaID);
    List<SocialMedia> findSocialMediaByContactId(Long contactID);
}
