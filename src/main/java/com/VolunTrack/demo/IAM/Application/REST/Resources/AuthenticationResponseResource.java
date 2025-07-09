// src/main/java/com/VolunTrack/demo/IAM/Application/REST/Resources/AuthenticationResponseResource.java

package com.VolunTrack.demo.IAM.Application.REST.Resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseResource {
    private String token;
    private Long organizationId;
}