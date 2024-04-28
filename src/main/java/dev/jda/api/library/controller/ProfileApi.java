package dev.jda.api.library.controller;

import dev.jda.api.library.requestmapping.RequestPath;
import dev.jda.model.library.ProfileDTO;
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

@Tag(name = "Profile", description = "Profile applicatie Endpoints")
@RequestMapping(RequestPath.V1 + RequestPath.PROFILE)
public interface ProfileApi {

    /**
     * @param uuid of the profile to be displayed.
     * @return  ProfileDTO with the values of the profile.
     */
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(summary = "Profile weergeven op basis van uuid.")
    ProfileDTO getProfileByCode(@PathVariable(value = "uuid")
                            @Parameter(example = "bc249d76-617a-4dfa-be47e7effeab8", description = "Filter PROFILE uuid") String uuid);

    /**
     * @param uuid of the profile to be updated.
     * @param profileDTO   with the values to be updated.
     * @return      ProfileDTO with the updated values.
     */
    @PutMapping(path = "/putProfile/{uuid}")
    @Operation(summary = "Bestaande profile bijwerken.")
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")
    //@PreAuthorize("hasAuthority('admin:READ)")
    @ResponseStatus(HttpStatus.OK)
    ProfileDTO putProfileByUuid(@RequestParam(value = "uuid") String uuid,
                            @RequestBody ProfileDTO profileDTO);

    /**
     * @param uuid of the profile to be updated.
     * @param profileDTO   with the values to be updated.
     * @return      ProfileDTO with the updated values.
     */
    @PatchMapping(path = "/patchProfile/{uuid}")
    @Operation(summary = "Bestaande profile bijwerken.")
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")
    //@PreAuthorize("hasAuthority('admin:READ)")
    @ResponseStatus(HttpStatus.OK)
    ProfileDTO patchProfileByUuid(@RequestParam(value = "uuid") String uuid,
                            @RequestBody ProfileDTO profileDTO);

    /**
     * Filter on UUID and delete the profile.
     *
     * @param uuid of the profile to be deleted.
     */
    @DeleteMapping(path = "/{uuid}")
    @Operation(summary = "Profile verwijderen op basis van UUID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProfileByUuid(@PathVariable(value = "uuid")  @Parameter(example = "bc249d76-617a-4dfa-be47e7effeab8") String uuid);

}
