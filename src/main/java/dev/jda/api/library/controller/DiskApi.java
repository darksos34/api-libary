package dev.jda.api.library.controller;

import dev.jda.api.library.requestmapping.RequestPath;
import dev.jda.model.library.DiskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "DM", description = "Demo applicatie Endpoints")
@RequestMapping(RequestPath.V1 + RequestPath.DISK)
public interface DiskApi {

    @PostMapping
    void createDisk();

    @PutMapping
    void updateDisk();


    @PatchMapping(path = "/{id}")
    @Operation(summary = "Bestaande disk bijwerken.")
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")
    //@PreAuthorize("hasAuthority('admin:READ)")
    @ResponseStatus(HttpStatus.OK)
    DiskDTO patchDiskByUuid(@RequestParam(value = "uuid") String uuid,
                            @RequestBody DiskDTO diskDTO);

}
