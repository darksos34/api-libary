package dev.jda.apilibary.controller;

import dev.jda.apilibary.hal.DemoRepresentationAssembler;
import dev.jda.apilibary.entity.Demo;
import dev.jda.apilibary.service.DemoService;


import dev.jda.demomodellibary.DemoDTO;
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
    @SuppressWarnings (value="unchecked")
    public PagedModel<DemoDTO> getAllDemosPageable(Pageable pageable) {
        Page<Demo> demoPage =  demoService.getAllDemosPageable(pageable);
        if(!demoPage.isEmpty()) {
         return (PagedModel<DemoDTO>) pagedResourcesAssembler.toEmptyModel(demoPage, DemoDTO.class);
        }
        return pagedResourcesAssembler.toModel(demoPage, demoRepresentationAssembler);
    }

    @Override
    public DemoDTO getDemoByCode(String code) {
        return demoRepresentationAssembler.toModel(demoService.getDemoByCode(code));
    }

    @Override
    public DemoDTO createDemo(DemoDTO demoDTO) {
        Demo demo = modelMapper.map(demoDTO, Demo.class);
        return demoRepresentationAssembler.toModel(demoService.saveDemo(demo));
    }

    @Override
    public DemoDTO patchDemoByUuid(String uuid, DemoDTO demoDTO) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID must not be null");
        }
        Demo demo = modelMapper.map(demoDTO, Demo.class);
        return demoRepresentationAssembler.toModel(demoService.saveDemoByUuid(demo.getUuid(), demo));
    }
}



