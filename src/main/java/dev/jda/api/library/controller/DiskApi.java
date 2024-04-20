package dev.jda.api.library.controller;

import dev.jda.api.library.requestmapping.RequestPath;
import dev.jda.model.library.DiskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "DR", description = "Disk applicatie Endpoints")
@RequestMapping(RequestPath.V1 + RequestPath.DISK)
public interface DiskApi {

    /**
     * @param uuid of the disk to be displayed.
     * @return  DiskDTO with the values of the disk.
     */
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(summary = "Disk weergeven op basis van uuid.")
    DiskDTO getDiskByCode(@PathVariable(value = "uuid")
                            @Parameter(example = "bc249d76-617a-4dfa-be47e7effeab8", description = "Filter DISK uuid") String uuid);

    //TODO PutDTO
    /**
     * @param uuid of the disk to be updated.
     * @param diskDTO   with the values to be updated.
     * @return      DiskDTO with the updated values.
     */
    @PutMapping(path = "/putDisk/{uuid}")
    @Operation(summary = "Bestaande disk bijwerken.")
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")
    //@PreAuthorize("hasAuthority('admin:READ)")
    @ResponseStatus(HttpStatus.OK)
    DiskDTO putDiskByUuid(@RequestParam(value = "uuid") String uuid,
                            @RequestBody DiskDTO diskDTO);

    //TODO PatchDTO
    /**
     * @param uuid of the disk to be updated.
     * @param diskDTO   with the values to be updated.
     * @return      DiskDTO with the updated values.
     */
    @PatchMapping(path = "/patchDisk/{uuid}")
    @Operation(summary = "Bestaande disk bijwerken.")
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")
    //@PreAuthorize("hasAuthority('admin:READ)")
    @ResponseStatus(HttpStatus.OK)
    DiskDTO patchDiskByUuid(@RequestParam(value = "uuid") String uuid,
                            @RequestBody DiskDTO diskDTO);

    /**
     * Filter on UUID and delete the disk.
     *
     * @param uuid of the disk to be deleted.
     */
    @DeleteMapping(path = "/{uuid}")
    @Operation(summary = "Disk verwijderen op basis van UUID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDiskByUuid(@PathVariable(value = "uuid")  @Parameter(example = "bc249d76-617a-4dfa-be47e7effeab8") String uuid);

}
