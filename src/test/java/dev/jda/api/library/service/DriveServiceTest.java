package dev.jda.api.library.service;

import dev.jda.api.library.entity.Drive;
import dev.jda.api.library.repository.DriveRepository;
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
class DriveServiceTest {

    public static final String DRIVE_CODE = "1234";

    @Mock
    protected DriveRepository driveRepository;

    private DriveService unitToTest;

    @BeforeEach
    public void beforeEachTest(){
        this.unitToTest = new DriveService(driveRepository);
    }

    @Test
    void testGetDriveInfo(){
        when(driveRepository.findByCode(any())).thenReturn(Optional.of(createDrive()));
        Drive result = unitToTest.getDriveByCode(DRIVE_CODE);
        assertEquals(createDrive(), result);
    }

    @Test
    void testGetAllDrivesPageable_HappyPath() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Drive> drivePage = new PageImpl<>(Collections.nCopies(5, createDrive()), pageable, 5);

        when(driveRepository.findAll(pageable)).thenReturn(drivePage);

        assertEquals(drivePage, unitToTest.getAllDriversPageable(pageable));
    }

    @Test
    void testSaveDrive_HappyPath() {
        Drive drive = createDrive();

        when(driveRepository.existsByCode(drive.getCode())).thenReturn(false);
        when(driveRepository.save(any(Drive.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Drive result = unitToTest.saveDrive(drive);

        assertEquals(drive.getCode(), result.getCode());
        assertEquals(drive.getName(), result.getName());
        assertEquals(drive.getUuid(), result.getUuid());
    }

    @Test
    void saveDrive_CodeExists() {
        Drive drive = createDrive();

        when(driveRepository.existsByCode(drive.getCode())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> unitToTest.saveDrive(drive));
    }
    @Test
    void testSaveDriveByUuid_EntityNotFound() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Drive newDrive = createDrive();

        when(driveRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unitToTest.patchDriveByUuid(uuid, newDrive));
    }

    @Test
    void testSaveDriveByUuid_HappyPath_WithArgumentCaptor() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Drive existingDrive = createDrive();
        Drive newDrive = Drive.builder()
                .code("5678")
                .name("piet")
                .uuid(uuid)
                .build();

        when(driveRepository.findByUuid(uuid)).thenReturn(Optional.of(existingDrive));
        when(driveRepository.save(any(Drive.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Drive> driveCaptor = ArgumentCaptor.forClass(Drive.class);

        Drive result = unitToTest.patchDriveByUuid(uuid, newDrive);

        verify(driveRepository).save(driveCaptor.capture());
        Drive capturedDrive = driveCaptor.getValue();

        assertEquals(newDrive.getCode(), capturedDrive.getCode());
        assertEquals(newDrive.getName(), capturedDrive.getName());
        assertEquals(newDrive.getUuid(), capturedDrive.getUuid());

        assertEquals(newDrive.getCode(), result.getCode());
        assertEquals(newDrive.getName(), result.getName());
        assertEquals(newDrive.getUuid(), result.getUuid());
    }
    @Test
    void testSaveDriveByUuid_NullFieldsInNewDrive() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Drive existingDrive = createDrive();
        Drive newDrive = Drive.builder()
                .code(null)
                .name(null)
                .uuid(null)
                .build();

        when(driveRepository.findByUuid(uuid)).thenReturn(Optional.of(existingDrive));
        when(driveRepository.save(any(Drive.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Drive result = unitToTest.patchDriveByUuid(uuid, newDrive);

        assertEquals(existingDrive.getCode(), result.getCode());
        assertEquals(existingDrive.getName(), result.getName());
        assertEquals(existingDrive.getUuid(), result.getUuid());
    }
    @Test
    void shouldDeleteDriveWhenUuidExists() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Drive drive = createDrive();

        when(driveRepository.findByUuid(uuid)).thenReturn(Optional.of(drive));

        unitToTest.deleteDriveByUuid(uuid);

        verify(driveRepository).delete(drive);
    }

    private Drive createDrive(){
        return Drive.builder()
                .code("1234")
                .name("henk")
                .uuid("f3as5jj-8819-9952-b3ds-l0os8iwwejsa")
                .build();
    }
}
