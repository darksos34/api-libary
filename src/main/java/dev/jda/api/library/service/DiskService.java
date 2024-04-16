package dev.jda.api.library.service;


import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.repository.DiskRepository;
import dev.jda.model.library.DiskDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DiskService {

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
    public void deleteDisk(String uuid) {
        log.info("Deleting disk");
        diskRepository.deleteById(uuid);
    }
    public  Disk patchDisk(String uuid, DiskDTO diskDTO) {
        log.info("Patching disk");
        Disk disk = diskRepository.findById(uuid).orElse(null);
        if (disk == null) {
            return null;
        }
        disk.setName(diskDTO.getName());
        return diskRepository.save(disk);
    }

    public Disk putDisk(String uuid, DiskDTO diskDTO) {
        Disk    disk = diskRepository.findById(uuid).orElse(null);
        if (!disk.getName().equals(diskDTO.getName()) || !disk.getUuid().equals(uuid)){
            return  saveDisk(uuid, diskDTO);
        }
        return disk;
    }
}
