package dev.jda.api.library.service;

import dev.jda.api.library.config.ModelMapperConfiguration;
import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.entity.User;
import dev.jda.api.library.exception.GlobalExceptionHandler;
import dev.jda.api.library.repository.ProfileRepository;
import dev.jda.api.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
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
@Import({ModelMapperConfiguration.class})
class UserServiceTest {

    public static final String USER_CODE = "1234";

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected ProfileRepository profileRepository;

    private UserService unitToTest;

    @BeforeEach
    public void beforeEachTest(){
        this.unitToTest = new UserService(userRepository, profileRepository);
    }

    @Test
    void testGetUserInfo(){
        when(userRepository.findByCode(any())).thenReturn(Optional.of(createUser()));
        User result = unitToTest.getUserByCode(USER_CODE);
        assertEquals(createUser(), result);
    }

    @Test
    void testGetAllUsersPageable_HappyPath() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> userPage = new PageImpl<>(Collections.nCopies(5, createUser()), pageable, 5);

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        assertEquals(userPage, unitToTest.getAllUserrsPageable(pageable));
    }

    @Test
    void testSaveUser_HappyPath() throws GlobalExceptionHandler.CodeExistsExceptionHandler {
        User user = createUser();

        when(userRepository.existsByCode(user.getCode())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = unitToTest.saveUser(user);

        assertEquals(user.getCode(), result.getCode());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getUuid(), result.getUuid());
    }

    @Test
    void saveUser_CodeExists() {
        User user = createUser();

        when(userRepository.existsByCode(user.getCode())).thenReturn(true);

        assertThrows(GlobalExceptionHandler.CodeExistsExceptionHandler.class, () -> unitToTest.saveUser(user));
    }
    @Test
    void testSaveUserByUuid_EntityNotFound() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        User newUser = createUser();

        when(userRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unitToTest.patchUserByUuid(uuid, newUser));
    }

    @Test
    void testSaveUserByUuid_HappyPath_WithArgumentCaptor() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        User existingUser = createUser();
        User newUser = User.builder()
                .code("5678")
                .name("piet")
                .uuid(uuid)
                .build();

        when(userRepository.findByUuid(uuid)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        User result = unitToTest.patchUserByUuid(uuid, newUser);

        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(newUser.getCode(), capturedUser.getCode());
        assertEquals(newUser.getName(), capturedUser.getName());
        assertEquals(newUser.getUuid(), capturedUser.getUuid());

        assertEquals(newUser.getCode(), result.getCode());
        assertEquals(newUser.getName(), result.getName());
        assertEquals(newUser.getUuid(), result.getUuid());
    }
    @Test
    void testSaveUserByUuid_NullFieldsInNewUser() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        User existingUser = createUser();
        User newUser = User.builder()
                .code(null)
                .name(null)
                .uuid(null)
                .build();

        when(userRepository.findByUuid(uuid)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = unitToTest.patchUserByUuid(uuid, newUser);

        assertEquals(existingUser.getCode(), result.getCode());
        assertEquals(existingUser.getName(), result.getName());
        assertEquals(existingUser.getUuid(), result.getUuid());
    }


    @Test
    void createProfileReturnsProfileWhenUserExists() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        User user = createUser();
        Profile profile = new Profile();

        when(userRepository.findByUuid(uuid)).thenReturn(Optional.of(user));
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = unitToTest.createProfile(uuid, profile);

        assertEquals(profile, result);
    }

    @Test
    void createProfileThrowsExceptionWhenUserDoesNotExist() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Profile profile = new Profile();

        when(userRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> unitToTest.createProfile(uuid, profile));
    }

    @Test
    void createProfileThrowsExceptionWhenUserCodeExists() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        Profile profile = new Profile();

        when(userRepository.findByUuid(uuid)).thenReturn(Optional.empty());
        when(userRepository.existsByCode(uuid)).thenReturn(true);

        assertThrows(EntityNotFoundException.class, () -> unitToTest.createProfile(uuid, profile));
    }


    @Test
    void shouldDeleteUserWhenUuidExists() {
        String uuid = "f3as5jj-8819-9952-b3ds-l0os8iwwejsa";
        User user = createUser();

        when(userRepository.findByUuid(uuid)).thenReturn(Optional.of(user));

        unitToTest.deleteUserByUuid(uuid);

        verify(userRepository).delete(user);
    }

    private User createUser(){
        return User.builder()
                .code("1234")
                .name("henk")
                .uuid("f3as5jj-8819-9952-b3ds-l0os8iwwejsa")
                .build();
    }
}
