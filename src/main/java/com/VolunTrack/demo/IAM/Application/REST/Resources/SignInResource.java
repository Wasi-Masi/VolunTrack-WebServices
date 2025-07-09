package com.VolunTrack.demo.IAM.Application.REST.Resources;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResource {

    @NotBlank(message = "{NotBlank.username}")
    private String username;

    @NotBlank(message = "{NotBlank.password}")
    private String password;
}
