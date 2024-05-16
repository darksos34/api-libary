package dev.jda.api.library.hal;

import dev.jda.api.library.controller.UserController;
import dev.jda.api.library.entity.User;
import dev.jda.model.library.dto.ProfileDTO;
import dev.jda.model.library.dto.UserDTO;
import io.micrometer.common.lang.NonNullApi;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
@NonNullApi
public class UserRepresentationAssembler implements RepresentationModelAssembler<User, UserDTO> {

    private final ModelMapper modelMapper;
    private final ProfileRepresentationAssembler profileReprestoModel;

    @Override
    public UserDTO toModel(User entity) {
        UserDTO userDTO = modelMapper.map(entity, UserDTO.class);
        addSelfLink(userDTO);
        userDTO.setProfiles(getProfilesAsModel(entity));
        return userDTO;
    }

    public List<ProfileDTO> getProfilesAsModel(User entity) {
        if(entity.getProfiles() == null) return Collections.emptyList();
        return entity.getProfiles().stream()
                .map(profileReprestoModel::toModel)
                .toList();
    }

    private void addSelfLink(UserDTO userDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserByCode(userDTO.getCode())).withSelfRel();
        userDTO.add(selfLink);
    }
}
