package dev.jda.api.library.controller;

import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.hal.ProfileRepresentationAssembler;
import dev.jda.api.library.service.ProfileService;
import dev.jda.model.library.ProfileDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController implements ProfileApi {

    private final ProfileService profileService;
    private final ModelMapper modelMapper;
    private final ProfileRepresentationAssembler profileRepresentationAssembler;

    @Override
    public ProfileDTO getProfileByCode(String uuid) {
        return profileRepresentationAssembler.toModel(profileService.getProfileByUuid(uuid));
    }

    @Override
    public ProfileDTO putProfileByUuid(String uuid, ProfileDTO profileDTO) {
        Profile profile = modelMapper.map(profileDTO, Profile.class);
        return profileRepresentationAssembler.toModel(profileService.putProfileByUuid(uuid, profile));
    }

    @Override
    public ProfileDTO patchProfileByUuid(String uuid, ProfileDTO profileDTO) {
        Profile profile = modelMapper.map(profileDTO, Profile.class);
        return profileRepresentationAssembler.toModel(profileService.updateProfileByUuid(uuid, profile));
    }

    @Override
    public void deleteProfileByUuid(String uuid) {
        profileService.deleteProfileByUuid(uuid);
    }
}
