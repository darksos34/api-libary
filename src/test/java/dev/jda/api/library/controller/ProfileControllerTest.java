package dev.jda.api.library.controller;

import dev.jda.api.library.config.ModelMapperConfiguration;
import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.hal.ProfileRepresentationAssembler;
import dev.jda.api.library.service.ProfileService;
import dev.jda.model.library.dto.ProfileDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({ ProfileService.class, ProfileController.class, ProfileRepresentationAssembler.class, ModelMapperConfiguration.class
})
@WebMvcTest(ProfileController.class)
@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    public static final String PROFILE_JSON = "{\"uuid\":\"test-uuid\", \"name\":\"test-name\"}";

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ProfileService unitToTest;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPatchProfileByUuidController() throws  Exception{
        when(unitToTest.updateProfileByUuid(anyString(), any())).thenReturn(createProfile());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/v1/profile/{uuid}", "1234")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(PROFILE_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.uuid", is("1234")))
                .andExpect((ResultMatcher) jsonPath("$.code", is("A1B2")))
                .andExpect((ResultMatcher) jsonPath("$.date", is((LocalDateTime.of(2021, 1, 1, 0, 0)))))
                .andExpect((ResultMatcher) jsonPath("$.name", is("profile")));
    }

    @Test
    void patchProfileByUuidHappyPath() throws Exception {
        when(unitToTest.updateProfileByUuid(anyString(), any(Profile.class)))
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
        Profile inputProfileDTO = new Profile();
        ProfileDTO expectedProfileDTO = new ProfileDTO();
        when(unitToTest.updateProfileByUuid(uuid, modelMapper.map(inputProfileDTO, Profile.class))).thenReturn(createProfile());

        // Act
        Profile result = unitToTest.updateProfileByUuid(uuid, inputProfileDTO);

        // Assert
        verify(unitToTest).updateProfileByUuid(uuid, modelMapper.map(inputProfileDTO, Profile.class));
        assertEquals(expectedProfileDTO, result);
    }

    @Test
    void deleteProfileByUuidHappyPath() {
        // Arrange
        String uuid = "test-uuid";
        doNothing().when(unitToTest).deleteProfileByUuid(uuid);

        // Act
        unitToTest.deleteProfileByUuid(uuid);

        // Assert
        verify(unitToTest).deleteProfileByUuid(uuid);
    }

    private Profile createProfile() {
        return Profile.builder()
                .uuid("1234")
                .name("profile")
                .code("A1B2")
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .build();
    }
}
