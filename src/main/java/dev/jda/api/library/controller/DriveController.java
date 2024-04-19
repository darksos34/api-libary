package dev.jda.api.library.controller;

import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.entity.Drive;
import dev.jda.api.library.hal.DriveRepresentationAssembler;
import dev.jda.api.library.service.DriveService;

import dev.jda.model.library.DriveDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;

import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DriveController implements DriveApi {

    private final DriveService driveService;
    private final ModelMapper modelMapper;
    private final DriveRepresentationAssembler driveRepresentationAssembler;
    private final PagedResourcesAssembler<Drive> pagedResourcesAssembler;

    @Override
    public DriveDTO getDriveByCode(String code) {
        return driveRepresentationAssembler.toModel(driveService.getDriveByCode(code));
    }

    @Override
    @SuppressWarnings (value="unchecked")
    public PagedModel<DriveDTO> getAllDriversPageable(Pageable pageable) {
        Page<Drive> drivePage =  driveService.getAllDriversPageable(pageable);
        if(!drivePage.isEmpty()) {
            return (PagedModel<DriveDTO>) pagedResourcesAssembler.toEmptyModel(drivePage, DriveDTO.class);
        }
        return pagedResourcesAssembler.toModel(drivePage, driveRepresentationAssembler);
    }

    @Override
    public DriveDTO createDrive(DriveDTO driveDTO) {
        Drive drive = modelMapper.map(driveDTO, Drive.class);
        return driveRepresentationAssembler.toModel(driveService.saveDrive(drive));
    }

    @Override
    public DriveDTO patchDriveByUuid(String uuid, DriveDTO driveDTO) {
        Drive drive = modelMapper.map(driveDTO, Drive.class);
        return driveRepresentationAssembler.toModel(driveService.patchDriveByUuid(uuid, drive));

    }

    @Override
    public DriveDTO createDriver(DriveDTO driveDTO, Disk disk) {
        Drive drive = modelMapper.map(driveDTO, Drive.class);
        return driveRepresentationAssembler.toModel(driveService.createDriveWithDisk(drive, disk));

}
    @Override
    public void deleteDriveByUuid(String uuid) {
        driveService.deleteDriveByUuid(uuid);
    }
}



