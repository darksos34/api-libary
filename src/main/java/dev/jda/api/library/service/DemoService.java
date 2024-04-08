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

    public Demo getDemoByCode(String code) {
        return demoRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Demo met code '%s' is niet gevonden", code)));
    }

    public Page<Demo> getAllDemosPageable(Pageable pageable) {
        return demoRepository.findAll(pageable);
    }

    public Demo saveDemo(Demo demo) {
        if (demoRepository.existsByCode(demo.getCode())) {
            throw new DemoCodeExistsException(demo.getCode());
        }
        return demoRepository.save(demo);
    }

    public Demo saveDemoByUuid(String uuid, Demo demo) {
        Demo existingDemo = demoRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, DEMO_NOTFOUND)));

        Optional.ofNullable(demo.getCode()).ifPresent(existingDemo::setCode);
        Optional.ofNullable(demo.getName()).ifPresent(existingDemo::setName);
        Optional.ofNullable(demo.getUuid()).ifPresent(existingDemo::setUuid);

        return demoRepository.save(existingDemo);

    }
}
