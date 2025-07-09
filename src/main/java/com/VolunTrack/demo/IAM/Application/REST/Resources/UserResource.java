// src/main/java/com/VolunTrack/demo/IAM/Application/REST/Resources/UserResource.java
package com.VolunTrack.demo.IAM.Application.REST.Resources;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserResource {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String plan;
    private String description;
    private String profilePictureUrl;
    private String bannerPictureUrl;
    private Long organizationId;
}