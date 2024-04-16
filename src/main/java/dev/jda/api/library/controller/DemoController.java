package dev.jda.api.library.controller;

import dev.jda.api.library.hal.DemoRepresentationAssembler;
import dev.jda.api.library.entity.Demo;
import dev.jda.api.library.service.DemoService;

import dev.jda.model.library.DemoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;

import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DemoController implements DemoApi {

    private final DemoService demoService;
    private final ModelMapper modelMapper;
    private final DemoRepresentationAssembler demoRepresentationAssembler;
    private final PagedResourcesAssembler<Demo> pagedResourcesAssembler;

    @Override
    public DemoDTO getDemoByCode(String code) {
        return demoRepresentationAssembler.toModel(demoService.getDemoByCode(code));
    }

    @Override
    @SuppressWarnings (value="unchecked")
    public PagedModel<DemoDTO> getAllDemosPageable(Pageable pageable) {
        Page<Demo> demoPage =  demoService.getAllDemosPageable(pageable);
        if(!demoPage.isEmpty()) {
            return (PagedModel<DemoDTO>) pagedResourcesAssembler.toEmptyModel(demoPage, DemoDTO.class);
        }
        return pagedResourcesAssembler.toModel(demoPage, demoRepresentationAssembler);
    }

    @Override
    public DemoDTO createDemo(DemoDTO demoDTO) {
        Demo demo = modelMapper.map(demoDTO, Demo.class);
        return demoRepresentationAssembler.toModel(demoService.saveDemo(demo));
    }

    @Override
    public DemoDTO patchDemoByUuid(String uuid, DemoDTO demoDTO) {
        Demo demo = modelMapper.map(demoDTO, Demo.class);
        return demoRepresentationAssembler.toModel(demoService.patchDemoByUuid(uuid, demo));

    }

    @Override
    public void deleteDemoByUuid(String uuid) {
        demoService.deleteDemoByUuid(uuid);
    }
}



