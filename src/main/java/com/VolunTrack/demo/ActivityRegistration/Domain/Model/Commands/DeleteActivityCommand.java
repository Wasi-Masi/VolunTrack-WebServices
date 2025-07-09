package com.VolunTrack.demo.ActivityRegistration.Domain.Model.Commands;

/**
 * Represents a command to delete an existing activity.
 * This command encapsulates the necessary data to delete an activity by its unique identifier.
 * In a CQRS (Command Query Responsibility Segregation) architecture,
 * this command is part of the write model, used to trigger the deletion of an activity.
 */
public record DeleteActivityCommand(Long actividadId) {
}
