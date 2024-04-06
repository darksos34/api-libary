package dev.jda.api.library.controller;

import dev.jda.api.library.requestmapping.RequestPath;

import dev.jda.model.library.DemoDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "DM", description = "Demo applicatie Endpoints")
@RequestMapping(RequestPath.V1 + RequestPath.DEMO)
public interface DemoApi {

    @GetMapping()
    @Operation(summary = "Lijst weergeven met alle demos als paging.")
    @Parameter(name = "page", schema = @Schema(type = "integer", defaultValue = "0"), in = ParameterIn.QUERY)
    @Parameter(name = "size", schema = @Schema(type = "integer", defaultValue = "20"), in = ParameterIn.QUERY)
    PagedModel<?> getAllDemosPageable(@ParameterObject @Parameter(hidden = true) Pageable pageable);

    @GetMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Operation(summary = "")
    DemoDTO getDemoByCode(@PathVariable(value = "code")
                          @Parameter(example = "ABCD", description = "test") String code);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation
    @ResponseBody()
    DemoDTO createDemo(@Valid @RequestBody DemoDTO demoDTO);

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Bestaande demo bijwerken.")
    @Parameter( name = "uuid", example = "bc249d76-617a-4dfa-be47e7effeab8")

    //@PreAuthorize("hasAuthority('admin:READ)")
    @ResponseStatus(HttpStatus.OK)
    DemoDTO patchDemoByUuid(@RequestParam(value = "uuid") String uuid,
                            @RequestBody DemoDTO demoDTO);

}
