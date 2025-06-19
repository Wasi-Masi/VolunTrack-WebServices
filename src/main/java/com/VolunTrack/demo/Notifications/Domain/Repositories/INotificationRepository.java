package com.VolunTrack.demo.Notifications.Domain.Repositories;

import com.VolunTrack.demo.Notifications.Domain.Model.Aggregates.Notification;
import com.VolunTrack.demo.Notifications.Domain.Model.Enums.RecipientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientIdAndRecipientType(Long recipientId, RecipientType recipientType);
}