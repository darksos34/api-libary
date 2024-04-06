package dev.jda.apilibary.service;

import dev.jda.apilibary.entity.Demo;
import dev.jda.apilibary.repository.DemoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    void getDemoInfo(){
        when(demoRepository.findByCode(any())).thenReturn(Optional.of(createDemo()));
        Demo result = unitToTest.getDemoByCode(DEMO_CODE);
        assertEquals(createDemo(), result);
    }

    @Test
    void getDemoNotFound(){
        when(demoRepository.findByCode(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unitToTest.getDemoByCode(DEMO_CODE));
    }
    @Test
    void patchProductByUuidTest() {
        // Arrange
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Demo demo = Demo.builder()
                .code("1234")
                .name("henk")
                .uuid(uuid)
                .build();
        Demo updatedDemo = Demo.builder()
                .code("5678")
                .name("piet")
                .uuid(uuid)
                .build();

        when(demoRepository.findById(uuid)).thenReturn(Optional.of(demo));
        when(demoRepository.save(any(Demo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        unitToTest.saveDemoByUuid(uuid, updatedDemo);

        // Assert
        assertEquals(updatedDemo.getCode(), demo.getCode());
        assertEquals(updatedDemo.getName(), demo.getName());
        assertEquals(updatedDemo.getUuid(), demo.getUuid());
    }

    private Demo createDemo(){
        return Demo.builder()
                .code("1234")
                .name("henk")
                .uuid("f3as5jj-8819-9952-b3ds-l0os8iwwejsa")
                .build();
    }
}
