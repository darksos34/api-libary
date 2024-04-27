package dev.jda.api.library.service;


import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.repository.DiskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DiskService {

    private static final String DISK_NOTFOUND = "Could not find the disk with UUID: %s";
    private final DiskRepository diskRepository;

    /**
     * @param uuid the uuid of the disk to get
     * @return  the disk with the given uuid
     */
    public Disk getDiskByUuid(String uuid) {
        log.info("Getting disk");
        return diskRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(DISK_NOTFOUND, uuid)));
    }

    /**
     * @param uuid the uuid of the disk to patch
     * @param disk  the disk to patch
     * @return      the patched disk
     */
    public Disk updateDiskByUuid(String uuid, Disk disk) {
        Disk existingDisk = diskRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DISK_NOTFOUND)));

        Optional.ofNullable(disk.getCode()).ifPresent(existingDisk::setCode);
        Optional.ofNullable(disk.getName()).ifPresent(existingDisk::setName);
        Optional.ofNullable(disk.getUuid()).ifPresent(existingDisk::setUuid);

        return diskRepository.save(existingDisk);
    }
    /**
     * @param uuid the uuid of the disk to delete
     */
    public void deleteDiskByUuid(String uuid) {
        Disk disk = diskRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DISK_NOTFOUND)));
        diskRepository.delete(disk);
    }

    /**
     * @param uuid  the uuid of the disk to put
     * @param disk  the disk to put
     * @return    the put disk
     */
    public Disk putDiskByUuid(String uuid,Disk disk) {
        Disk existingDisk = diskRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DISK_NOTFOUND)));
        existingDisk.setCode(disk.getCode());
        existingDisk.setName(disk.getName());
        existingDisk.setUuid(disk.getUuid());
        return diskRepository.save(existingDisk);
    }
}
