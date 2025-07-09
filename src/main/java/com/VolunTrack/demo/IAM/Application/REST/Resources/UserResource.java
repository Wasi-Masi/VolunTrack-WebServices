// src/main/java/com/VolunTrack/demo/IAM/Application/REST/resources/UserResource.java
package com.VolunTrack.demo.IAM.Application.REST.Resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResource {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String plan;
    private String description;
    private String profilePictureUrl;
    private String bannerPictureUrl;


}