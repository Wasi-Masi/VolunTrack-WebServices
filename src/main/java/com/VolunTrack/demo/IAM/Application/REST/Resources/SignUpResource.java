package com.VolunTrack.demo.IAM.Application.REST.Resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResource {

    @NotBlank(message = "{NotBlank.username}")
    @Size(min = 3, max = 50, message = "{Size.username}")
    private String username;

    @NotBlank(message = "{NotBlank.password}")
    @Size(min = 6, max = 100, message = "{Size.password}")
    private String password;

    @NotBlank(message = "{NotBlank.email}")
    @Email(message = "{Email.email}")
    private String email;

    @NotBlank(message = "{NotBlank.phoneNumber}")
    private String phoneNumber;

    @NotBlank(message = "{NotBlank.plan}")
    private String plan;

    @NotBlank(message = "{NotBlank.description}")
    private String description;

    @NotBlank(message = "{NotBlank.profilePictureUrl}")
    private String profilePictureUrl;

    @NotBlank(message = "{NotBlank.bannerPictureUrl}")
    private String bannerPictureUrl;
}