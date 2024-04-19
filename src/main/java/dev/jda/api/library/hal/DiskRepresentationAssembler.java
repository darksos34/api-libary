package dev.jda.api.library.hal;

import dev.jda.api.library.controller.DiskController;
import dev.jda.api.library.entity.Disk;
import dev.jda.model.library.DiskDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class DiskRepresentationAssembler implements RepresentationModelAssembler<Disk, DiskDTO> {

    private final ModelMapper modelMapper;

    @Override
    public DiskDTO toModel(Disk disk) {
        DiskDTO diskDTO = modelMapper.map(disk, DiskDTO.class);
        addSelfLink(diskDTO);
        return diskDTO;
    }

    private void addSelfLink(DiskDTO diskDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DiskController.class).getDiskByCode(diskDTO.getCode())).withSelfRel();
        diskDTO.add(selfLink);
    }
}
