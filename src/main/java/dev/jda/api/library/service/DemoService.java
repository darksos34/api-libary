package dev.jda.api.library.service;

import dev.jda.api.library.exception.GlobalExceptionHandler.DemoCodeExistsException;
import dev.jda.api.library.repository.DemoRepository;
import dev.jda.api.library.entity.Demo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemoService {
    private static final String DEMO_NOTFOUND = "Kon de demo niet vinden op basis van UUID";
    private final DemoRepository demoRepository;

    /**
     * Get a demo by its code from the database
     * @param code the code of the demo
     *             to get from the database
     * @return  the demo with the given code
     */
    public Demo getDemoByCode(String code) {
        return demoRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Demo met code '%s' is niet gevonden", code)));
    }

    /**
     * Get all demos from the database
     *
     * @param pageable the pageable object
     *                 containing the page number and size
     * @return  a pageable list of demos
     */
    public Page<Demo> getAllDemosPageable(Pageable pageable) {
        return demoRepository.findAll(pageable);
    }

    /**
     * Save a demo to the database
     *
     * @param demo Creata a demo to save to the database
     * @return  the saved demo
     * @throws  DemoCodeExistsException if the code already exists
     */
    public Demo saveDemo(Demo demo) {
        if (demoRepository.existsByCode(demo.getCode())) {
            throw new DemoCodeExistsException(demo.getCode());
        }
        return demoRepository.save(demo);
    }

    /**
     * Update a demo by its given uuid to the database
     *
     * @param uuid of the demo to update
     * @param demo  the demo to update
     * @return  the updated demo
     * @throws EntityNotFoundException if the demo is not found
     */
    public Demo saveDemoByUuid(String uuid, Demo demo) {
        Demo existingDemo = demoRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DEMO_NOTFOUND)));

        Optional.ofNullable(demo.getCode()).ifPresent(existingDemo::setCode);
        Optional.ofNullable(demo.getName()).ifPresent(existingDemo::setName);
        Optional.ofNullable(demo.getUuid()).ifPresent(existingDemo::setUuid);

        return demoRepository.save(existingDemo);
    }

    /**
     * Delete a demo by its uuid
     *
     * @param uuid of the demo to delete
     * @throws EntityNotFoundException if the demo is not found
     */
    public void deleteDemoByUuid(String uuid) {
      Demo demo = demoRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DEMO_NOTFOUND)));
        demoRepository.delete(demo);
    }
}
