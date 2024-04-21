package dev.jda.api.library.hal;

import dev.jda.api.library.controller.DriveController;
import dev.jda.api.library.entity.Drive;
import dev.jda.model.library.DriveDTO;
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
public class DriveRepresentationAssembler implements RepresentationModelAssembler<Drive, DriveDTO> {

    private final ModelMapper modelMapper;

    @Override
    public DriveDTO toModel(Drive drive) {
        DriveDTO driveDTO = modelMapper.map(drive, DriveDTO.class);
        addSelfLink(driveDTO);
        return driveDTO;
    }

    private void addSelfLink(DriveDTO driveDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DriveController.class).getDriveByCode(driveDTO.getCode())).withSelfRel();
        driveDTO.add(selfLink);
    }
}
