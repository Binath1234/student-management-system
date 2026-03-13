package com.sms.student_management_system.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * AuditLog Entity - tracks all administrative actions in the system.
 * Implements audit trail requirement for tracking system changes.
 * 
 * Design Pattern: Observer Pattern
 * - This entity acts as the observer for system state changes
 * - Automatically logs all CRUD operations on Student and Course entities
 * 
 * @author Student Management System
 * @version 1.0
 */
@Entity
@Table(name = "audit_log", indexes = {
    @Index(name = "idx_timestamp", columnList = "timestamp"),
    @Index(name = "idx_entity_type", columnList = "entity_type"),
    @Index(name = "idx_action", columnList = "action")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "action", nullable = false, length = 20)
    private String action; // ADD, UPDATE, DELETE, SEARCH, VIEW

    @Column(name = "actor", nullable = false, length = 50)
    private String actor; // Username of the person performing the action

    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType; // STUDENT or COURSE

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "old_value", length = 1000)
    private String oldValue; // Serialized old values

    @Column(name = "new_value", length = 1000)
    private String newValue; // Serialized new values

    @Column(name = "description", length = 500)
    private String description; // Human-readable description of the change

    // ==================== Constructors ====================

    /**
     * Default constructor (required by JPA)
     */
    public AuditLog() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructor with audit details
     */
    public AuditLog(String action, String actor, String entityType, Long entityId) {
        this.timestamp = LocalDateTime.now();
        this.action = action;
        this.actor = actor;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    /**
     * Constructor with full details including values
     */
    public AuditLog(String action, String actor, String entityType, Long entityId,
                    String oldValue, String newValue) {
        this.timestamp = LocalDateTime.now();
        this.action = action;
        this.actor = actor;
        this.entityType = entityType;
        this.entityId = entityId;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", action='" + action + '\'' +
                ", actor='" + actor + '\'' +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                '}';
    }
}
