package dev.jda.api.library.controller;

import dev.jda.api.library.config.ModelMapperConfiguration;
import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.hal.ProfileRepresentationAssembler;
import dev.jda.api.library.repository.ProfileRepository;
import dev.jda.api.library.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({ ProfileService.class, ProfileController.class, ProfileRepresentationAssembler.class, ModelMapperConfiguration.class
})
@WebMvcTest(ProfileController.class)
@ExtendWith(SpringExtension.class)
class ProfileControllerTest {

    public static final String PROFILE_JSON = "{\"code\":\"A1B2\", \"name\":\"profile\"}";

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private ProfileService unitToTest;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPatchProfileByUuid() throws  Exception{
        when(unitToTest.updateProfileByUuid(anyString(), any())).thenReturn(createProfile());

        RequestBuilder request = MockMvcRequestBuilders
                .patch("/v1/profile/patchProfile/{uuid}", createProfile().getUuid())
                .accept(MediaType.APPLICATION_JSON)
                .content(PROFILE_JSON)
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("A1B2")))
                .andExpect(jsonPath("$.name", is("profile")));

    }



    @Test
    void testDeleteProfileByUuid() throws Exception {

        doNothing().when(unitToTest).deleteProfileByUuid(createProfile().getUuid());

        mockMvc.perform(delete("/v1/profile/" + createProfile().getUuid())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private Profile createProfile() {
        return Profile.builder()
                .name("profile")
                .code("A1B2")
                .build();
    }
}
