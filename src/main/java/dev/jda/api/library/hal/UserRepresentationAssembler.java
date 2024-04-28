package dev.jda.api.library.hal;

import dev.jda.api.library.controller.UserController;
import dev.jda.api.library.entity.User;
import dev.jda.model.library.UserDTO;
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
public class UserRepresentationAssembler implements RepresentationModelAssembler<User, UserDTO> {

    private final ModelMapper modelMapper;

    @Override
    public UserDTO toModel(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        addSelfLink(userDTO);
        return userDTO;
    }

    private void addSelfLink(UserDTO userDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserByCode(userDTO.getCode())).withSelfRel();
        userDTO.add(selfLink);
    }
}
