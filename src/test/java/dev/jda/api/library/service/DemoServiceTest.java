package dev.jda.api.library.service;

import dev.jda.api.library.entity.Demo;
import dev.jda.api.library.repository.DemoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@Import(ModelMapperConfiguration.class)
class DemoServiceTest {

    public static final String DEMO_CODE = "1234";

    @Mock
    protected DemoRepository demoRepository;

    private DemoService unitToTest;

    @BeforeEach
    public void beforeEachTest(){
        this.unitToTest = new DemoService(demoRepository);
    }

    @Test
    void testGetDemoInfo(){
        when(demoRepository.findByCode(any())).thenReturn(Optional.of(createDemo()));
        Demo result = unitToTest.getDemoByCode(DEMO_CODE);
        assertEquals(createDemo(), result);
    }

    @Test
    void testGetAllDemosPageable_HappyPath() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Demo> demoPage = new PageImpl<>(Collections.nCopies(5, createDemo()), pageable, 5);

        when(demoRepository.findAll(pageable)).thenReturn(demoPage);

        assertEquals(demoPage, unitToTest.getAllDemosPageable(pageable));
    }

    @Test
    void testSaveDemo_HappyPath() {
        Demo demo = createDemo();

        when(demoRepository.existsByCode(demo.getCode())).thenReturn(false);
        when(demoRepository.save(any(Demo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Demo result = unitToTest.saveDemo(demo);

        assertEquals(demo.getCode(), result.getCode());
        assertEquals(demo.getName(), result.getName());
        assertEquals(demo.getUuid(), result.getUuid());
    }

    @Test
    void saveDemo_CodeExists() {
        Demo demo = createDemo();

        when(demoRepository.existsByCode(demo.getCode())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> unitToTest.saveDemo(demo));
    }
    @Test
    void testSaveDemoByUuid_EntityNotFound() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Demo newDemo = createDemo();

        when(demoRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unitToTest.saveDemoByUuid(uuid, newDemo));
    }

    @Test
    void testSaveDemoByUuid_HappyPath_WithArgumentCaptor() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Demo existingDemo = createDemo();
        Demo newDemo = Demo.builder()
                .code("5678")
                .name("piet")
                .uuid(uuid)
                .build();

        when(demoRepository.findByUuid(uuid)).thenReturn(Optional.of(existingDemo));
        when(demoRepository.save(any(Demo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Demo> demoCaptor = ArgumentCaptor.forClass(Demo.class);

        Demo result = unitToTest.saveDemoByUuid(uuid, newDemo);

        verify(demoRepository).save(demoCaptor.capture());
        Demo capturedDemo = demoCaptor.getValue();

        assertEquals(newDemo.getCode(), capturedDemo.getCode());
        assertEquals(newDemo.getName(), capturedDemo.getName());
        assertEquals(newDemo.getUuid(), capturedDemo.getUuid());

        assertEquals(newDemo.getCode(), result.getCode());
        assertEquals(newDemo.getName(), result.getName());
        assertEquals(newDemo.getUuid(), result.getUuid());
    }
    @Test
    void testSaveDemoByUuid_NullFieldsInNewDemo() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Demo existingDemo = createDemo();
        Demo newDemo = Demo.builder()
                .code(null)
                .name(null)
                .uuid(null)
                .build();

        when(demoRepository.findByUuid(uuid)).thenReturn(Optional.of(existingDemo));
        when(demoRepository.save(any(Demo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Demo result = unitToTest.saveDemoByUuid(uuid, newDemo);

        assertEquals(existingDemo.getCode(), result.getCode());
        assertEquals(existingDemo.getName(), result.getName());
        assertEquals(existingDemo.getUuid(), result.getUuid());
    }

    private Demo createDemo(){
        return Demo.builder()
                .code("1234")
                .name("henk")
                .uuid("f3as5jj-8819-9952-b3ds-l0os8iwwejsa")
                .build();
    }
}
