package dev.jda.api.library.controller;

import dev.jda.api.library.config.ModelMapperConfiguration;
import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.hal.ProfileRepresentationAssembler;
import dev.jda.api.library.hal.UserRepresentationAssembler;
import dev.jda.api.library.service.ProfileService;
import dev.jda.model.library.ProfileDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({ModelMapper.class, ProfileService.class, ProfileController.class, ProfileRepresentationAssembler.class, ModelMapperConfiguration.class, PagedResourcesAssembler.class
})
@WebMvcTest(ProfileController.class)
@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    public static final String PROFILE_JSON = "{\"uuid\":\"test-uuid\", \"name\":\"test-name\"}";

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProfileService profileService;

    @Mock
    private MockMvc mockMvc;

    @InjectMocks
    private ProfileController profileController;

    @Test
    void putProfileByUuidHappyPath() {
        // Arrange
        String uuid = "test-uuid";
        ProfileDTO inputProfileDTO = new ProfileDTO();
        ProfileDTO expectedProfileDTO = new ProfileDTO();
        when(profileService.putProfileByUuid(uuid, modelMapper.map(inputProfileDTO, Profile.class))).thenReturn(expectedProfileDTO);
        when(profileService.getProfileByUuid(anyString())).thenReturn(new ProfileDTO());
        doAnswer(invocation -> {
            // Your stubbing logic here
            return null; // for void methods
        }).when(profileService).putProfileByUuid(anyString(), any(Profile.class));
        // Act
        ProfileDTO result = profileController.putProfileByUuid(uuid, inputProfileDTO);

        // Assert
        verify(profileService).putProfileByUuid(uuid, modelMapper.map(inputProfileDTO, Profile.class));
        assertEquals(expectedProfileDTO, result);
    }

    @Test
    void patchProfileByUuidHappyPath() throws Exception {
        when(profileService.updateProfileByUuid(anyString(), any(Profile.class)))
                .thenReturn(createProfile());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/profiles/{uuid}", "test-uuid")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(PROFILE_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.uuid", is("test-uuid")))
                .andExpect((ResultMatcher) jsonPath("$.name", is("test-name")));

        // Arrange
        String uuid = "test-uuid";
        ProfileDTO inputProfileDTO = new ProfileDTO();
        ProfileDTO expectedProfileDTO = new ProfileDTO();
        when(profileService.updateProfileByUuid(uuid, modelMapper.map(inputProfileDTO, Profile.class))).thenReturn(expectedProfileDTO);

        // Act
        ProfileDTO result = profileController.patchProfileByUuid(uuid, inputProfileDTO);

        // Assert
        verify(profileService).updateProfileByUuid(uuid, modelMapper.map(inputProfileDTO, Profile.class));
        assertEquals(expectedProfileDTO, result);
    }

    @Test
    void deleteProfileByUuidHappyPath() {
        // Arrange
        String uuid = "test-uuid";
        doNothing().when(profileService).deleteProfileByUuid(uuid);

        // Act
        profileController.deleteProfileByUuid(uuid);

        // Assert
        verify(profileService).deleteProfileByUuid(uuid);
    }

    private Profile createProfile() {
        return Profile.builder()
                .uuid("test-uuid")
                .name("test-name")
                .build();
    }
}
