package dev.jda.api.library.controller;

import dev.jda.api.library.service.DiskService;
import dev.jda.model.library.DiskDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiskController implements DiskApi{

    private final DiskService diskService;
    private final ModelMapper modelMapper;


    @Override
    public void createDisk() {

    }

    @Override
    public void updateDisk() {

    }

    @Override
    public DiskDTO patchDiskByUuid(String uuid, DiskDTO diskDTO) {
        return  modelMapper.map(diskService.patchDisk(uuid, diskDTO), DiskDTO.class);
    }
}
