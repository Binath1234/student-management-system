package com.sms.student_management_system.service;

import com.sms.student_management_system.entity.AuditLog;
import com.sms.student_management_system.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Audit Log Service - Business logic for audit trail management.
 * 
 * Responsibilities:
 * - Retrieve audit logs by various criteria
 * - Generate audit reports
 * - Monitor system activities
 * 
 * @author Student Management System
 * @version 1.0
 */
@Service
@Transactional
public class AuditLogService {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogService.class);

    @Autowired
    private AuditLogRepository auditLogRepository;

    /**
     * Get all audit logs.
     * 
     * @return List of all audit logs
     */
    public List<AuditLog> getAllAuditLogs() {
        logger.debug("Fetching all audit logs");
        return auditLogRepository.findAll();
    }

    /**
     * Get audit logs for a specific entity type.
     * 
     * @param entityType The type of entity (STUDENT or COURSE)
     * @return List of audit logs for the entity type
     */
    public List<AuditLog> getAuditLogsByEntityType(String entityType) {
        logger.debug("Fetching audit logs for entity type: {}", entityType);
        return auditLogRepository.findByEntityType(entityType);
    }

    /**
     * Get audit logs for a specific entity ID.
     * 
     * @param entityId The entity ID
     * @return List of audit logs for the entity
     */
    public List<AuditLog> getAuditLogsByEntityId(Long entityId) {
        logger.debug("Fetching audit logs for entity ID: {}", entityId);
        return auditLogRepository.findByEntityId(entityId);
    }

    /**
     * Get audit logs by action type.
     * 
     * @param action The action (ADD, UPDATE, DELETE, SEARCH, VIEW)
     * @return List of audit logs for the action
     */
    public List<AuditLog> getAuditLogsByAction(String action) {
        logger.debug("Fetching audit logs for action: {}", action);
        return auditLogRepository.findByAction(action);
    }

    /**
     * Get audit logs by actor (user who performed the action).
     * 
     * @param actor The actor/username
     * @return List of audit logs for the actor
     */
    public List<AuditLog> getAuditLogsByActor(String actor) {
        logger.debug("Fetching audit logs for actor: {}", actor);
        return auditLogRepository.findByActor(actor);
    }

    /**
     * Get audit logs within a time range.
     * 
     * @param startTime The start timestamp
     * @param endTime The end timestamp
     * @return List of audit logs within the time range
     */
    public List<AuditLog> getAuditLogsInRange(LocalDateTime startTime, LocalDateTime endTime) {
        logger.debug("Fetching audit logs between {} and {}", startTime, endTime);
        return auditLogRepository.findByTimestampBetween(startTime, endTime);
    }

    /**
     * Get recent audit logs.
     * 
     * @return List of recent audit logs
     */
    public List<AuditLog> getRecentAuditLogs() {
        logger.debug("Fetching recent audit logs");
        return auditLogRepository.findRecentLogs();
    }

    /**
     * Get audit logs for specific entity type and action.
     * 
     * @param entityType The type of entity
     * @param action The action performed
     * @return List of matching audit logs
     */
    public List<AuditLog> getAuditLogs(String entityType, String action) {
        logger.debug("Fetching audit logs for entity type: {} and action: {}", entityType, action);
        return auditLogRepository.findByEntityTypeAndAction(entityType, action);
    }

    /**
     * Get total count of audit logs.
     * 
     * @return Total number of audit log entries
     */
    public long getTotalAuditLogCount() {
        logger.debug("Fetching total audit log count");
        return auditLogRepository.count();
    }

    /**
     * Generate audit report for a specific entity.
     * Useful for compliance and monitoring.
     * 
     * @param entityType The type of entity
     * @param entityId The entity ID
     * @return String representation of the audit history
     */
    public String generateAuditReport(String entityType, Long entityId) {
        logger.info("Generating audit report for {} with ID: {}", entityType, entityId);
        
        List<AuditLog> logs = auditLogRepository.findByEntityId(entityId);
        
        StringBuilder report = new StringBuilder();
        report.append("===== AUDIT REPORT =====\n");
        report.append("Entity Type: ").append(entityType).append("\n");
        report.append("Entity ID: ").append(entityId).append("\n");
        report.append("Total Actions: ").append(logs.size()).append("\n");
        report.append("========================\n\n");

        for (AuditLog log : logs) {
            report.append("Timestamp: ").append(log.getTimestamp()).append("\n");
            report.append("Action: ").append(log.getAction()).append("\n");
            report.append("Actor: ").append(log.getActor()).append("\n");
            if (log.getDescription() != null) {
                report.append("Description: ").append(log.getDescription()).append("\n");
            }
            report.append("---\n");
        }

        return report.toString();
    }
}
