package dev.jda.api.library.controller;

import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.entity.Drive;
import dev.jda.api.library.hal.DiskRepresentationAssembler;
import dev.jda.api.library.service.DiskService;
import dev.jda.model.library.DiskDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiskController implements DiskApi {

    private final DiskService diskService;
    private final ModelMapper modelMapper;
    private final DiskRepresentationAssembler diskRepresentationAssembler;
    private final PagedResourcesAssembler<Drive> pagedResourcesAssembler;

    @Override
    public void createDisk() {

    }

    @Override
    public void updateDisk() {

    }

    @Override
    public DiskDTO patchDiskByUuid(String uuid, DiskDTO diskDTO) {
        Disk disk = modelMapper.map(diskDTO, Disk.class);
        return diskRepresentationAssembler.toModel(diskService.patchDiskByUuid(uuid, disk));
    }
}
