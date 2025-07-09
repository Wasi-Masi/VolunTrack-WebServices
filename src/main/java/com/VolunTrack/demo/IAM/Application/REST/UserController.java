package com.VolunTrack.demo.IAM.Application.REST;

import com.VolunTrack.demo.IAM.Application.REST.Resources.UpdateUserResource;
import com.VolunTrack.demo.IAM.Application.REST.Resources.UserResource;
import com.VolunTrack.demo.IAM.Domain.Model.Aggregates.User;
import com.VolunTrack.demo.IAM.Infrastructure.Repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User Management Endpoints")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Operation(summary = "Get a user", description = "Gets user.")
    @GetMapping("/me")
    public ResponseEntity<UserResource> getCurrentUserProfile() {
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
            // ------------------------------------------

            return ResponseEntity.ok(userResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Update user", description = "Updates user by its id")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResource> updateUserProfile(@PathVariable Long userId,
                                                          @Valid @RequestBody UpdateUserResource resource) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();

            if (!userToUpdate.getUsername().equals(authenticatedUsername)) {
                return ResponseEntity.status(403).build(); // Prohibido
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
            // ----------------------------------------------------------------------------------

            return ResponseEntity.ok(userResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}