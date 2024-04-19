package dev.jda.api.library.controller;

import dev.jda.api.library.entity.Disk;
import dev.jda.api.library.requestmapping.RequestPath;
import dev.jda.model.library.DriveDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "DM", description = "Drive applicatie Endpoints")
@RequestMapping(RequestPath.V1 + RequestPath.DRIVE)
public interface DriveApi {

    /**
     * @param code of the drive to be displayed.
     * @return  DriveDTO with the values of the drive.
     */
    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(summary = "Drive weergeven op basis van code.")
    DriveDTO getDriveByCode(@PathVariable(value = "code")
                          @Parameter(example = "ABCD", description = "Filter DRIVE code") String code);

    /**
     * @param pageable Paging parameters.
     * @return  Pageable list with all drives.
     */
    @GetMapping()
    @Operation(summary = "Lijst weergeven met alle drives als paging.")
    @Parameter(name = "page", schema = @Schema(type = "integer", defaultValue = "0"), in = ParameterIn.QUERY)
    @Parameter(name = "size", schema = @Schema(type = "integer", defaultValue = "20"), in = ParameterIn.QUERY)
    PagedModel<?> getAllDriversPageable(@ParameterObject @Parameter(hidden = true) Pageable pageable);

    /**
     * @param driveDTO with the values to be created.
     * @return  DriveDTO with the created values.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAuthority('admin:READ)")
    @Operation(summary = "Nieuwe drive aanmaken.")
    @ResponseBody
    DriveDTO createDrive(@Valid @RequestBody DriveDTO driveDTO);

    /**
     * @param uuid   Patch Drive and filter based on UUID to be updated.
     * @param driveDTO   DriveDTO with the new values.
     * @return  DriveDTO with the updated values.
     */
    @PatchMapping(path = "/{id}")
    @Operation(summary = "Bestaande drive bijwerken.")
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")
    //@PreAuthorize("hasAuthority('admin:READ)")
    @ResponseStatus(HttpStatus.OK)
    DriveDTO patchDriveByUuid(@RequestParam(value = "uuid") String uuid,
                              @RequestBody DriveDTO driveDTO);

    @PostMapping("/createdriveDisk")
    DriveDTO createDriver(@RequestBody DriveDTO driveDTO, Disk disk) ;

    /**
     * Filter on UUID and delete the drive.
     *
     * @param uuid of the drive to be deleted.
     */
    @DeleteMapping(path = "/{uuid}")
    @Operation(summary = "Drive verwijderen op basis van UUID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDriveByUuid(@PathVariable(value = "uuid")    @Parameter(example = "bc249d76-617a-4dfa-be47e7effeab8") String uuid);
}
