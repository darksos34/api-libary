package dev.jda.api.library.repository;

import dev.jda.api.library.entity.Disk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiskRepository extends JpaRepository<Disk, String> {

    Optional<Disk> findByUuid(String uuid);
}
