// src/main/java/com/VolunTrack/demo/iam/domain/model/aggregates/User.java

package com.VolunTrack.demo.IAM.Domain.Model.Aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String plan;

    @Column(length = 1000)
    private String description;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "banner_picture_url")
    private String bannerPictureUrl;

}