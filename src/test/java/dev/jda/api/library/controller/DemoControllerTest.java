package dev.jda.api.library.controller;

import dev.jda.api.library.config.ModelMapperConfiguration;
import dev.jda.api.library.entity.Demo;
import dev.jda.api.library.hal.DemoRepresentationAssembler;
import dev.jda.api.library.repository.DemoRepository;
import dev.jda.api.library.service.DemoService;
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
@WebMvcTest(DemoController.class)
@Import({
        DemoService.class, ModelMapperConfiguration.class, DemoRepresentationAssembler.class
})
class DemoControllerTest {

    public static final String DEMO_JSON = "{\"code\":\"1234\", \"name\":\"henk\"}";

    @Getter
    @Setter
    @MockBean
    private DemoRepository demoRepository;

    @MockBean
    private DemoService demoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDemoByCode() throws Exception {
        when(demoService.getDemoByCode(anyString())).thenReturn(createDemo());

        mockMvc.perform(get("/v1/demo/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("1234")))
                .andExpect(jsonPath("$.name", is("henk")));

    }

    @Test
    void testGetDemoByCode_NotFound() throws Exception {
        when(demoService.getDemoByCode(anyString())).thenThrow(new EntityNotFoundException("Demo met code '1' is niet gevonden"));

        mockMvc.perform(get("/v1/demo/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteDemoByCode() {

        doNothing().when(demoService).deleteDemoByUuid(createDemo().getUuid());

        assertDoesNotThrow(() -> mockMvc.perform(delete("/v1/demo/" + createDemo().getUuid())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()));

        verify(demoService, times(1)).deleteDemoByUuid(createDemo().getUuid());
    }
    @Test
    void shouldThrowExceptionWhenUuidDoesNotExist() throws Exception {

        doThrow(new EntityNotFoundException()).when(demoService).deleteDemoByUuid(createDemo().getUuid());

        mockMvc.perform(delete("/v1/demo/" +  createDemo().getUuid())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(demoService, times(1)).deleteDemoByUuid(createDemo().getUuid());
    }
    private Demo createDemo(){
        return Demo.builder()
                .uuid("f3as5jj-8819-9952-b3ds-l0os8iwwejsa")
                .code("1234")
                .name("henk")
                .build();
    }
}
