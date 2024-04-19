package dev.jda.api.library.service;


import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.entity.Drive;
import dev.jda.api.library.repository.DiskRepository;
import dev.jda.api.library.repository.DriveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DiskService {

    private static final String DISK_NOTFOUND = "Kon de disk niet vinden op basis van UUID";
    private final DiskRepository diskRepository;
    private final DriveRepository driveRepository;

    public Disk createDisk(String driveId, Disk disk) {
        Optional<Drive> driveOptional = driveRepository.findById(driveId);
        if (driveOptional.isPresent()) {
            Drive drive = driveOptional.get();
            disk.setDrive(drive); // Stel de gebruiker in op het profiel
            return diskRepository.save(disk);
        } else {
            throw  new EntityNotFoundException("Drive not found");
        }
    }

    public Disk getDisk(String uuid) {
        log.info("Getting disk");
        return diskRepository.findById(uuid).orElse(null);
    }

    public Disk patchDiskByUuid(String uuid, Disk disk) {
        Disk existingDisk = diskRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DISK_NOTFOUND)));

        Optional.ofNullable(disk.getCode()).ifPresent(existingDisk::setCode);
        Optional.ofNullable(disk.getName()).ifPresent(existingDisk::setName);
        Optional.ofNullable(disk.getUuid()).ifPresent(existingDisk::setUuid);
        log.info("Patching disk", existingDisk);
        return diskRepository.save(existingDisk);
    }

    public void deleteDisk(String uuid) {
        log.info("Deleting disk");
        diskRepository.deleteById(uuid);
    }
}
