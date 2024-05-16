package dev.jda.api.library.hal;

import dev.jda.api.library.controller.ProfileController;
import dev.jda.api.library.entity.Profile;
import dev.jda.model.library.dto.ProfileDTO;
import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
@NonNullApi
public class ProfileRepresentationAssembler implements RepresentationModelAssembler<Profile, ProfileDTO> {

    private final ModelMapper modelMapper;

    /**
     * @param profile the profile to convert
     * @return  the converted profile
     */
    @Override
    public ProfileDTO toModel(Profile profile) {
        ProfileDTO profileDTO = modelMapper.map(profile, ProfileDTO.class);
        addSelfLink(profileDTO);
        return profileDTO;
    }

    private void addSelfLink(ProfileDTO profileDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(ProfileController.class).getProfileByUuid(profileDTO.getUuid())).withSelfRel();
        profileDTO.add(selfLink);
    }
}
