package dev.jda.api.library.controller;

import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.hal.DiskRepresentationAssembler;
import dev.jda.api.library.service.DiskService;
import dev.jda.model.library.DiskDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiskController implements DiskApi {

    private final DiskService diskService;
    private final ModelMapper modelMapper;
    private final DiskRepresentationAssembler diskRepresentationAssembler;

    @Override
    public DiskDTO getDiskByCode(String uuid) {
        return diskRepresentationAssembler.toModel(diskService.getDiskByUuid(uuid));
    }

    @Override
    public DiskDTO putDiskByUuid(String uuid, DiskDTO diskDTO) {
        Disk disk = modelMapper.map(diskDTO, Disk.class);
        return diskRepresentationAssembler.toModel(diskService.putDiskByUuid(uuid, disk));
    }

    @Override
    public DiskDTO patchDiskByUuid(String uuid, DiskDTO diskDTO) {
        Disk disk = modelMapper.map(diskDTO, Disk.class);
        return diskRepresentationAssembler.toModel(diskService.patchDiskByUuid(uuid, disk));
    }

    @Override
    public void deleteDiskByUuid(String uuid) {
        diskService.deleteDiskByUuid(uuid);
    }
}
