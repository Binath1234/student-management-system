package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for AuditLog entity.
 * Provides access to audit trail records for system actions.
 * 
 * @author Student Management System
 * @version 1.0
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /**
     * Find all audit logs for a specific entity type
     */
    @Query("SELECT a FROM AuditLog a WHERE a.entityType = :entityType ORDER BY a.timestamp DESC")
    List<AuditLog> findByEntityType(@Param("entityType") String entityType);

    /**
     * Find all audit logs for a specific entity ID
     */
    @Query("SELECT a FROM AuditLog a WHERE a.entityId = :entityId ORDER BY a.timestamp DESC")
    List<AuditLog> findByEntityId(@Param("entityId") Long entityId);

    /**
     * Find audit logs by action type
     */
    @Query("SELECT a FROM AuditLog a WHERE a.action = :action ORDER BY a.timestamp DESC")
    List<AuditLog> findByAction(@Param("action") String action);

    /**
     * Find audit logs by actor (user who performed the action)
     */
    @Query("SELECT a FROM AuditLog a WHERE a.actor = :actor ORDER BY a.timestamp DESC")
    List<AuditLog> findByActor(@Param("actor") String actor);

    /**
     * Find audit logs within a time range
     */
    @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :startTime AND :endTime ORDER BY a.timestamp DESC")
    List<AuditLog> findByTimestampBetween(@Param("startTime") LocalDateTime startTime, 
                                          @Param("endTime") LocalDateTime endTime);

    /**
     * Find recent audit logs (last n entries)
     */
    @Query(value = "SELECT a FROM AuditLog a ORDER BY a.timestamp DESC")
    List<AuditLog> findRecentLogs();

    /**
     * Find audit logs for specific entity and action
     */
    @Query("SELECT a FROM AuditLog a WHERE a.entityType = :entityType AND a.action = :action ORDER BY a.timestamp DESC")
    List<AuditLog> findByEntityTypeAndAction(@Param("entityType") String entityType, 
                                             @Param("action") String action);
}
