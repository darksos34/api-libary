package dev.jda.api.library.controller;

import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.exception.GlobalExceptionHandler;
import dev.jda.api.library.requestmapping.RequestPath;
import dev.jda.model.library.UserDTO;
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

@Tag(name = "User", description = "User applicatie Endpoints")
@RequestMapping(RequestPath.V1 + RequestPath.USER)
public interface UserApi {

    /**
     * @param code of the user to be displayed.
     * @return  UserDTO with the values of the user.
     */
    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(summary = "User weergeven op basis van code.")
    UserDTO getUserByCode(@PathVariable(value = "code")
                          @Parameter(example = "ABCD", description = "Filter USER code") String code);

    /**
     * @param pageable Paging parameters.
     * @return  Pageable list with all users.
     */
    @GetMapping()
    @Operation(summary = "Lijst weergeven met alle users als paging.")
    @Parameter(name = "page", schema = @Schema(type = "integer", defaultValue = "0"), in = ParameterIn.QUERY)
    @Parameter(name = "size", schema = @Schema(type = "integer", defaultValue = "20"), in = ParameterIn.QUERY)
    PagedModel<?> getAllUserrsPageable(@ParameterObject @Parameter(hidden = true) Pageable pageable);

    /**
     * @param userDTO with the values to be created.
     * @return  UserDTO with the created values.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAuthority('admin:READ)")
    @Operation(summary = "Nieuwe user aanmaken.")
    @ResponseBody
    UserDTO createUser(@Valid @RequestBody UserDTO userDTO) throws GlobalExceptionHandler.CodeExistsExceptionHandler;



    /**
     * @param uuid   Patch User and filter based on UUID to be updated.
     * @param userDTO   UserDTO with the new values.
     * @return  UserDTO with the updated values.
     */
    @PatchMapping(path = "/{id}")
    @Operation(summary = "Bestaande user bijwerken.")
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")
    //@PreAuthorize("hasAuthority('admin:READ)")
    @ResponseStatus(HttpStatus.OK)
    UserDTO patchUserByUuid(@RequestParam(value = "uuid") String uuid,
                              @RequestBody UserDTO userDTO);

    /**
     *  Create Profile and filter based on UUID by User to be updated.
     * @param uuid  the values to be created.
     * @param profile   with the values to be created.
     * @return      UserDTO with the created values.
     */
    @PostMapping("/createuserProfile")
    @Operation(summary = "Nieuwe Profile aanmaken.")
    @ResponseBody
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")
    Profile createProfile(@RequestParam(value = "uuid") String uuid, @Valid @RequestBody Profile profile);

    /**
     * Filter on UUID and delete the user.
     * @param uuid of the user to be deleted.
     */
    @DeleteMapping(path = "/{uuid}")
    @Operation(summary = "User verwijderen op basis van UUID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserByUuid(@PathVariable(value = "uuid") @Parameter(example = "bc249d76-617a-4dfa-be47e7effeab8") String uuid);
}
