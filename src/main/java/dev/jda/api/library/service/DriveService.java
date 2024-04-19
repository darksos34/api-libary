package dev.jda.api.library.service;

import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.entity.Drive;
import dev.jda.api.library.exception.GlobalExceptionHandler.DriveCodeExistsException;
import dev.jda.api.library.repository.DiskRepository;
import dev.jda.api.library.repository.DriveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriveService {
    private static final String DRIVE_NOTFOUND = "Kon de drive niet vinden op basis van UUID";
    private final DriveRepository driveRepository;
    private final ModelMapper modelMapper;
    private final DiskRepository diskRepository;
    /**
     * Get a drive by its code from the database
     * @param code the code of the drive
     *             to get from the database
     * @return  the drive with the given code
     */
    public Drive getDriveByCode(String code) {
        return driveRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Drive met code '%s' is niet gevonden", code)));
    }

    /**
     * Get all drivers from the database
     *
     * @param pageable the pageable object
     *                 containing the page number and size
     * @return  a pageable list of drivers
     */
    public Page<Drive> getAllDriversPageable(Pageable pageable) {
        return driveRepository.findAll(pageable);
    }

    /**
     * Save a drive to the database
     *
     * @param drive Creat a drive to save to the database
     * @return  the saved drive
     * @throws  DriveCodeExistsException if the code already exists
     */
    public Drive saveDrive(Drive drive) {
        if (driveRepository.existsByCode(drive.getCode())) {
            throw new DriveCodeExistsException(drive.getCode());
        }
        return driveRepository.save(drive);
    }

    /**
     * Update a drive by its given uuid to the database
     *
     * @param uuid of the drive to update
     * @param drive  the drive to update
     * @return  the updated drive
     * @throws EntityNotFoundException if the drive is not found
     */
    public Drive patchDriveByUuid(String uuid, Drive drive) {
        Drive existingDrive = driveRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DRIVE_NOTFOUND)));

        Optional.ofNullable(drive.getCode()).ifPresent(existingDrive::setCode);
        Optional.ofNullable(drive.getName()).ifPresent(existingDrive::setName);
        Optional.ofNullable(drive.getUuid()).ifPresent(existingDrive::setUuid);

        return driveRepository.save(existingDrive);
    }
    public Drive createDriveWithDisk(Drive drive, Disk disk) {
        Drive savedDrive = driveRepository.save(drive);
        disk.setDrive(savedDrive);
        diskRepository.save(disk);
        return modelMapper.map(savedDrive, Drive.class);
    }
    /**
     * Delete a drive by its uuid
     *
     * @param uuid of the drive to delete
     * @throws EntityNotFoundException if the drive is not found
     */
    public void deleteDriveByUuid(String uuid) {
      Drive drive = driveRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DRIVE_NOTFOUND)));
        driveRepository.delete(drive);
    }
}
