// src/main/java/com/VolunTrack/demo/IAM/Application/REST/resources/SignInResource.java

package com.VolunTrack.demo.IAM.Application.REST.Resources;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResource {
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}