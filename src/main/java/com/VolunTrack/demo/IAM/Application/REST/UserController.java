package com.VolunTrack.demo.IAM.Application.REST;

import com.VolunTrack.demo.IAM.Application.REST.Resources.UpdateUserResource;
import com.VolunTrack.demo.IAM.Application.REST.Resources.UserResource;
import com.VolunTrack.demo.IAM.Domain.Model.Aggregates.User;
import com.VolunTrack.demo.IAM.Infrastructure.Repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import com.VolunTrack.demo.exception.ResourceNotFoundException;
import com.VolunTrack.demo.response.ApiResponse;

/**
 * REST Controller for User management.
 * Provides endpoints for retrieving and updating user profiles,
 * ensuring internationalization of messages and consistent API responses.
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "User Management Endpoints")
public class UserController {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public UserController(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }


    @Operation(summary = "Get a user", description = "Gets user.")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResource>> getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResource userResource = new UserResource();
            userResource.setId(user.getId());
            userResource.setUsername(user.getUsername());
            userResource.setEmail(user.getEmail());
            userResource.setPhoneNumber(user.getPhoneNumber());
            userResource.setPlan(user.getPlan());
            userResource.setDescription(user.getDescription());
            userResource.setProfilePictureUrl(user.getProfilePictureUrl());
            userResource.setBannerPictureUrl(user.getBannerPictureUrl());
            userResource.setOrganizationId(user.getOrganizationId());

            return ResponseEntity.ok(ApiResponse.success(userResource,
                    messageSource.getMessage("user.get.success", null, LocaleContextHolder.getLocale())));
        } else {
            throw new ResourceNotFoundException(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
        }
    }


    @Operation(summary = "Update user", description = "Updates user by its id")
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResource>> updateUserProfile(@PathVariable Long userId,
                                                                       @Valid @RequestBody UpdateUserResource resource) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();

            if (!userToUpdate.getUsername().equals(authenticatedUsername)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.<UserResource>error(
                        messageSource.getMessage("user.update.forbidden", null, LocaleContextHolder.getLocale()), null));
            }

            userToUpdate.setUsername(resource.getUsername());
            userToUpdate.setEmail(resource.getEmail());
            userToUpdate.setPhoneNumber(resource.getPhoneNumber());
            userToUpdate.setPlan(resource.getPlan());
            userToUpdate.setDescription(resource.getDescription());
            userToUpdate.setProfilePictureUrl(resource.getProfilePictureUrl());
            userToUpdate.setBannerPictureUrl(resource.getBannerPictureUrl());

            User updatedUser = userRepository.save(userToUpdate);

            UserResource userResource = new UserResource();
            userResource.setId(updatedUser.getId());
            userResource.setUsername(updatedUser.getUsername());
            userResource.setEmail(updatedUser.getEmail());
            userResource.setPhoneNumber(updatedUser.getPhoneNumber());
            userResource.setPlan(updatedUser.getPlan());
            userResource.setDescription(updatedUser.getDescription());
            userResource.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
            userResource.setBannerPictureUrl(updatedUser.getBannerPictureUrl());
            userResource.setOrganizationId(updatedUser.getOrganizationId());

            return ResponseEntity.ok(ApiResponse.success(userResource,
                    messageSource.getMessage("user.update.success", null, LocaleContextHolder.getLocale())));
        } else {
            throw new ResourceNotFoundException(messageSource.getMessage("user.not.found.by.id", new Object[]{userId}, LocaleContextHolder.getLocale()));
        }
    }
}