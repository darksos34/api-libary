package dev.jda.api.library.repository;

import dev.jda.api.library.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemoRepository extends JpaRepository<Demo, String> {

    Optional<Demo> findByCode(String code);
    Optional<Demo> findByUuid(String uuid);
    boolean existsByCode(String code);

}
