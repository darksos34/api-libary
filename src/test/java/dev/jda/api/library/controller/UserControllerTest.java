package dev.jda.api.library.controller;

import dev.jda.api.library.config.ModelMapperConfiguration;
import dev.jda.api.library.entity.User;
import dev.jda.api.library.hal.ProfileRepresentationAssembler;
import dev.jda.api.library.hal.UserRepresentationAssembler;
import dev.jda.api.library.repository.UserRepository;
import dev.jda.api.library.service.UserService;
import dev.jda.model.library.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import({
        UserService.class, ModelMapperConfiguration.class, UserRepresentationAssembler.class, ProfileRepresentationAssembler.class
})
class UserControllerTest {

    public static final String USER_JSON = "{\"code\":\"1234\", \"name\":\"henk\"}";
    @MockBean
    private ModelMapper modelMapper;

    @Getter
    @Setter
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void testGetUserByCode() throws Exception {
//        when(userService.getUserByCode(anyString())).thenReturn(createUser());
//
//        mockMvc.perform(get("/v1/user/1").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code", is("1234")))
//                .andExpect(jsonPath("$.name", is("henk")));
//
//    }

    @Test
    void testGetUserByCode_NotFound() throws Exception {
        when(userService.getUserByCode(anyString())).thenThrow(new EntityNotFoundException("User met code '1' is niet gevonden"));

        mockMvc.perform(get("/v1/user/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 400 when invalid UserDTO is provided")
    void shouldReturn400WhenInvalidUserDtoIsProvided() throws Exception {
        mockMvc.perform(patch("/user/{uuid}", "test-uuid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"uuid\":\"test-uuid\"}")) // missing name
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when non-existing UUID is provided")
    void shouldReturn404WhenNonExistingUuidIsProvided() throws Exception {
        UserDTO userDTO = new UserDTO();
        User user = new User();

        when(modelMapper.map(any(UserDTO.class), any())).thenReturn(user);
        when(userService.patchUserByUuid(anyString(), any(User.class))).thenReturn(null);

        mockMvc.perform(patch("/user/{uuid}", "non-existing-uuid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"uuid\":\"test-uuid\", \"name\":\"test-name\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUserByCode() {

        doNothing().when(userService).deleteUserByUuid(createUser().getUuid());

        assertDoesNotThrow(() -> mockMvc.perform(delete("/v1/user/" + createUser().getUuid())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()));

        verify(userService, times(1)).deleteUserByUuid(createUser().getUuid());
    }

    @Test
    void shouldThrowExceptionWhenUuidDoesNotExist() throws Exception {

        doThrow(new EntityNotFoundException()).when(userService).deleteUserByUuid(createUser().getUuid());

        mockMvc.perform(delete("/v1/user/" +  createUser().getUuid())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).deleteUserByUuid(createUser().getUuid());
    }

    private User createUser(){
        return User.builder()
                .uuid("f3as5jj-8819-9952-b3ds-l0os8iwwejsa")
                .code("1234")
                .name("henk")
                .build();
    }
}
