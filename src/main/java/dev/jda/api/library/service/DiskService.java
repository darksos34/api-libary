package dev.jda.api.library.service;


import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.repository.DiskRepository;
import dev.jda.model.library.DiskDTO;
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

    public Disk saveDisk(String uuid, DiskDTO diskDTO) {
        log.info("Saving disk");
        return diskRepository.save(Disk.builder()
                .uuid(uuid)
                .name(diskDTO.getName())
                .build());
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
