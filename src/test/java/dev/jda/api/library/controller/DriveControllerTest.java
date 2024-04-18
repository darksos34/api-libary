package dev.jda.api.library.controller;

import dev.jda.api.library.config.ModelMapperConfiguration;
import dev.jda.api.library.entity.Drive;
import dev.jda.api.library.hal.DriveRepresentationAssembler;
import dev.jda.api.library.repository.DriveRepository;
import dev.jda.api.library.service.DriveService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DriveController.class)
@Import({
        DriveService.class, ModelMapperConfiguration.class, DriveRepresentationAssembler.class
})
class DriveControllerTest {

    public static final String DRIVE_JSON = "{\"code\":\"1234\", \"name\":\"henk\"}";

    @Getter
    @Setter
    @MockBean
    private DriveRepository driveRepository;

    @MockBean
    private DriveService driveService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDriveByCode() throws Exception {
        when(driveService.getDriveByCode(anyString())).thenReturn(createDrive());

        mockMvc.perform(get("/v1/drive/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("1234")))
                .andExpect(jsonPath("$.name", is("henk")));

    }

    @Test
    void testGetDriveByCode_NotFound() throws Exception {
        when(driveService.getDriveByCode(anyString())).thenThrow(new EntityNotFoundException("Drive met code '1' is niet gevonden"));

        mockMvc.perform(get("/v1/drive/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteDriveByCode() {

        doNothing().when(driveService).deleteDriveByUuid(createDrive().getUuid());

        assertDoesNotThrow(() -> mockMvc.perform(delete("/v1/drive/" + createDrive().getUuid())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()));

        verify(driveService, times(1)).deleteDriveByUuid(createDrive().getUuid());
    }
    @Test
    void shouldThrowExceptionWhenUuidDoesNotExist() throws Exception {

        doThrow(new EntityNotFoundException()).when(driveService).deleteDriveByUuid(createDrive().getUuid());

        mockMvc.perform(delete("/v1/drive/" +  createDrive().getUuid())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(driveService, times(1)).deleteDriveByUuid(createDrive().getUuid());
    }
    private Drive createDrive(){
        return Drive.builder()
                .uuid("f3as5jj-8819-9952-b3ds-l0os8iwwejsa")
                .code("1234")
                .name("henk")
                .build();
    }
}
