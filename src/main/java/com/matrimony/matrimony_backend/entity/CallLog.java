package com.matrimony.matrimony_backend.entity;

import com.matrimony.matrimony_backend.enums.CallStatus;
import com.matrimony.matrimony_backend.enums.CallType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "call_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caller_id", nullable = false)
    private User caller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    private CallType callType;

    @Enumerated(EnumType.STRING)
    private CallStatus status;

    private Integer durationSeconds;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
