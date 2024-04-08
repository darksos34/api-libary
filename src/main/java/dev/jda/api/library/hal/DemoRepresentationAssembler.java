package dev.jda.api.library.hal;

import dev.jda.api.library.controller.DemoController;
import dev.jda.api.library.entity.Demo;
import dev.jda.model.library.DemoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
@RequiredArgsConstructor
public class DemoRepresentationAssembler implements RepresentationModelAssembler<Demo, DemoDTO> {

    private final ModelMapper modelMapper;

    @Override
    public DemoDTO toModel(Demo demo) {
        DemoDTO demoDTO = modelMapper.map(demo, DemoDTO.class);
        addSelfLink(demoDTO);
        return demoDTO;
    }

    private void addSelfLink(DemoDTO demoDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(DemoController.class).getDemoByCode(demoDTO.getCode())).withSelfRel();
        demoDTO.add(selfLink);
    }
}
