package dev.jda.api.library.service;


import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private static final String PROFILE_NOTFOUND = "Could not find the profile with UUID: %s";
    private final ProfileRepository profileRepository;

    /**
     * @param uuid the uuid of the profile to get
     * @return  the profile with the given uuid
     */
    public Profile getProfileByUuid(String uuid) {
        log.info("Getting profile");
        return profileRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PROFILE_NOTFOUND, uuid)));
    }

    /**
     * @param uuid the uuid of the profile to patch
     * @param profile  the profile to patch
     * @return      the patched profile
     */
    public Profile updateProfileByUuid(String uuid, Profile profile) {
        Profile existingProfile = profileRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, PROFILE_NOTFOUND)));

        Optional.ofNullable(profile.getCode()).ifPresent(existingProfile::setCode);
        Optional.ofNullable(profile.getName()).ifPresent(existingProfile::setName);
        Optional.ofNullable(profile.getUuid()).ifPresent(existingProfile::setUuid);

        return profileRepository.save(existingProfile);
    }
    /**
     * @param uuid the uuid of the profile to delete
     */
    public void deleteProfileByUuid(String uuid) {
        Profile profile = profileRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(uuid, PROFILE_NOTFOUND)));
        profileRepository.delete(profile);
    }

    /**
     * @param uuid  the uuid of the profile to put
     * @param profile  the profile to put
     * @return    the put profile
     */
    public Profile putProfileByUuid(String uuid, Profile profile) {
        Profile existingProfile = profileRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, PROFILE_NOTFOUND)));
        existingProfile.setCode(profile.getCode());
        existingProfile.setName(profile.getName());
        existingProfile.setUuid(profile.getUuid());
        return profileRepository.save(existingProfile);
    }
}
