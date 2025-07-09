// src/main/java/com/VolunTrack/demo/IAM/Application/REST/resources/SignUpResource.java

package com.VolunTrack.demo.IAM.Application.REST.Resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResource {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone Number cannot be empty")
    private String phoneNumber;

    @NotBlank(message = "Plan cannot be empty")
    private String plan;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Profile Picture URL cannot be empty")
    private String profilePictureUrl;

    @NotBlank(message = "Banner Picture URL cannot be empty")
    private String bannerPictureUrl;
}