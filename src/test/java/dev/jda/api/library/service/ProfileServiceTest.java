package dev.jda.api.library.service;

import dev.jda.api.library.config.ModelMapperConfiguration;
import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Import({ModelMapperConfiguration.class})
class ProfileServiceTest {

    private static final String PROFILE_UUID =  "1234" ;
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService unitToTest;

    @BeforeEach
    void setUp() {
        this.unitToTest = new ProfileService(profileRepository);
    }

    @Test
    void testGetProfileFound() {
        // Arrange: Set up any necessary data or mock behavior
        when(profileRepository.findByUuid(any())).thenReturn(Optional.of(createProfile()));

        // Act: Call the method under test
        Profile result = unitToTest.getProfileByUuid(PROFILE_UUID);

        // Assert: Check that the method behaved as expected
        assertEquals(createProfile(), result);
    }

    @Test
    void testGetProfileNotFound() {
        // Arrange: Set up any necessary data or mock behavior
        when(profileRepository.findByUuid(any())).thenReturn(Optional.empty());

        // Act: Call the method under test
        // Assert: Check that the method behaved as expected
        assertThrows(EntityNotFoundException.class, () -> unitToTest.getProfileByUuid(PROFILE_UUID));
    }

    @Test
    void testUpdateProfileByUuidSimple() {
        // Arrange: Set up any necessary data or mock behavior
        when(profileRepository.findByUuid(any())).thenReturn(Optional.of(createProfile()));
        when(profileRepository.save(any())).thenReturn(createProfile());

        // Act: Call the method under test
        Profile result = unitToTest.updateProfileByUuid(PROFILE_UUID, createProfile());

        // Assert: Check that the method behaved as expected
        assertEquals(createProfile(), result);
    }
    @Test
    void testPatchProfileByUuidUserExpert(){
        String uuid = "1234";
        Profile existingProfile = Profile.builder()
                .code(uuid)
                .name("John Doe")
                .code("ABCD")
                .build();
        Profile updateProfile = Profile.builder()
                .uuid(uuid)
                .name("Jane Doe")
                .code("EFGH")
                .build();
        when(profileRepository.findByUuid(uuid)).thenReturn(Optional.of(existingProfile));
        when(profileRepository.save(any((Profile.class)))).thenAnswer(inovaction -> inovaction.getArgument(0));

        ArgumentCaptor<Profile> profileArgumentCaptor = ArgumentCaptor.forClass(Profile.class);

        Profile result = unitToTest.updateProfileByUuid(uuid, updateProfile);

        verify(profileRepository).save(profileArgumentCaptor.capture());
        Profile capturedProfile = profileArgumentCaptor.getValue();

        assertEquals(updateProfile.getCode(), capturedProfile.getCode());
        assertEquals(updateProfile.getName(), capturedProfile.getName());
        assertEquals(updateProfile.getUuid(), capturedProfile.getUuid());

    }

    @Test
    void testDeleteProfileByUuid() {
        // Arrange: Set up any necessary data or mock behavior
        when(profileRepository.findByUuid(any())).thenReturn(Optional.of(createProfile()));

        // Act: Call the method under test
        unitToTest.deleteProfileByUuid(PROFILE_UUID);

        // Assert: Check that the method behaved as expected
        verify(profileRepository).delete(createProfile());
    }
    @Test
    void testDeleteProfileByUuidNotFound() {
        // Arrange: Set up any necessary data or mock behavior
        when(profileRepository.findByUuid(any())).thenReturn(Optional.empty());

        // Act: Call the method under test
        // Assert: Check that the method behaved as expected
        assertThrows(EntityNotFoundException.class, () -> unitToTest.deleteProfileByUuid(PROFILE_UUID));
    }

    @Test
    void testUpdateProfileByUuidNotFound() {
        // Arrange: Set up any necessary data or mock behavior
        when(profileRepository.findByUuid(any())).thenReturn(Optional.empty());

        // Act: Call the method under test
        // Assert: Check that the method behaved as expected
        assertThrows(EntityNotFoundException.class, () -> unitToTest.updateProfileByUuid(PROFILE_UUID, createProfile()));
    }


    private Profile createProfile() {
        return Profile.builder()
                .uuid("1234")
                .code("ABCD")
                .name("John Doe")
                .build();
    }
}
