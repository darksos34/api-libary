package dev.jda.api.library.service;

import dev.jda.api.library.entity.Profile;
import dev.jda.api.library.entity.User;
import dev.jda.api.library.exception.GlobalExceptionHandler;
import dev.jda.api.library.repository.ProfileRepository;
import dev.jda.api.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_NOTFOUND = "User with code '%s' was not found";
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    /**
     * Get a user by its code from the database
     * @param code the code of the user
     *             to get from the database
     * @return  the user with the given code
     */
    public User getUserByCode(String code) {
        return userRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOTFOUND)));
    }

    /**
     * Get all userrs from the database
     * @param pageable the pageable object
     *                 containing the page number and size
     * @return  a pageable list of userrs
     */
    public Page<User> getAllUserrsPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Save a user to the database
     * @param user Creat a user to save to the database
     * @return  the saved user
     * @throws GlobalExceptionHandler.CodeExistsExceptionHandler if the code already exists
     */
    public User saveUser(User user) throws GlobalExceptionHandler.CodeExistsExceptionHandler {
        if (userRepository.existsByCode(user.getCode())) {
            throw new GlobalExceptionHandler.CodeExistsExceptionHandler(user.getCode());
        }
        return userRepository.save(user);
    }
    /**
     * Update a user by its given uuid to the database
     * @param uuid of the user to update
     * @param user  the user to update
     * @return  the updated user
     * @throws EntityNotFoundException if the user is not found
     */
    public User patchUserByUuid(String uuid, User user) {
        User existingUser = userRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(String.format(uuid, USER_NOTFOUND)));

        Optional.ofNullable(user.getCode()).ifPresent(existingUser::setCode);
        Optional.ofNullable(user.getName()).ifPresent(existingUser::setName);
        Optional.ofNullable(user.getUuid()).ifPresent(existingUser::setUuid);

        return userRepository.save(existingUser);
    }

    /**
     * @param uuid create Profile and filter based on UUID by User to be updated.
     * @param profile  with the values to be created.
     * @return      ProfileDTO with the created values.
     */
    public Profile createProfile(String uuid, Profile profile) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        profile.setUser(user);
        return profileRepository.save(profile);
    }

    /**
     * Delete a user by its uuid
     * @param uuid of the user to delete
     * @throws EntityNotFoundException if the user is not found
     */
    public void deleteUserByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(uuid, USER_NOTFOUND)));
        userRepository.delete(user);
    }
    /**
     * @param uuid the uuid of the profile to get
     * @return  the profile with the given uuid
     */
    public User getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(uuid, USER_NOTFOUND)));
    }

}
