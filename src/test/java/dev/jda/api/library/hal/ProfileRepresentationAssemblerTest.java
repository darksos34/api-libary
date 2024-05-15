package dev.jda.api.library.hal;

import dev.jda.api.library.controller.ProfileController;
import dev.jda.api.library.entity.Profile;
import dev.jda.model.library.dto.ProfileDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExtendWith(MockitoExtension.class)
class ProfileRepresentationAssemblerTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProfileRepresentationAssembler unitToTest;

    private Profile profile;
    private ProfileDTO profileDTO;

    @BeforeEach
    void setUp() {
        profile = new Profile();
        profileDTO = new ProfileDTO();
    }

    @Test
    void toModelReturnsProfileDtoWithSelfLink() {
        when(modelMapper.map(profile, ProfileDTO.class)).thenReturn(profileDTO);

        ProfileDTO result = unitToTest.toModel(profile);

        verify(modelMapper).map(profile, ProfileDTO.class);
        assertEquals(profileDTO, result);
    }

    @Test
    void toModelAddsSelfLinkToProfileDto() {
        when(modelMapper.map(profile, ProfileDTO.class)).thenReturn(profileDTO);

        ProfileDTO result = unitToTest.toModel(profile);

        Link expectedLink = WebMvcLinkBuilder.linkTo(methodOn(ProfileController.class).getProfileByUuid(profileDTO.getCode())).withSelfRel();
        assertTrue(result.hasLink(expectedLink.getRel()));
        assertEquals(expectedLink, result.getLink(expectedLink.getRel()).get());
    }
}
