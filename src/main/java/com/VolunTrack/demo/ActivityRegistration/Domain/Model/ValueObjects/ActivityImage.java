package com.VolunTrack.demo.ActivityRegistration.Domain.Model.ValueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an image's attributes as a Value Object associated with an Activity.
 * It does not have its own identity and its existence depends on the Activity entity.
 */
@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ActivityImage {

    @Column(name = "image_url", nullable = false, length = 1000)
    private String imageUrl; // An image URL

    public ActivityImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}