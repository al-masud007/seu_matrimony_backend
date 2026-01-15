package com.matrimony.matrimony_backend.repository;

import com.matrimony.matrimony_backend.entity.Interest;
import com.matrimony.matrimony_backend.entity.User;
import com.matrimony.matrimony_backend.enums.InterestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InterestRepository extends JpaRepository<Interest, UUID> {
    List<Interest> findBySender(User sender);
    List<Interest> findByReceiver(User receiver);
    List<Interest> findByReceiverAndStatus(User receiver, InterestStatus status);
    Optional<Interest> findBySenderAndReceiver(User sender, User receiver);
}
