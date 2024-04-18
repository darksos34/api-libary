package dev.jda.api.library.repository;

import dev.jda.api.library.entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriveRepository extends JpaRepository<Drive, String> {

    Optional<Drive> findByCode(String code);
    Optional<Drive> findByUuid(String uuid);
    boolean existsByCode(String code);

}
