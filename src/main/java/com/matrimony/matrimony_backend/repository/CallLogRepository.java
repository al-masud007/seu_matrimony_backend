package com.matrimony.matrimony_backend.repository;

import com.matrimony.matrimony_backend.entity.CallLog;
import com.matrimony.matrimony_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, UUID> {
    
    @Query("SELECT c FROM CallLog c WHERE c.caller = :user OR c.receiver = :user ORDER BY c.createdAt DESC")
    List<CallLog> findByUser(@Param("user") User user);
}
