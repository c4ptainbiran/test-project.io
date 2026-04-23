package com.autoservice.repository;

import com.autoservice.model.Request;
import com.autoservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByClientOrderByCreatedAtDesc(User client);
    @Query("SELECT r FROM Request r JOIN FETCH r.client ORDER BY r.createdAt DESC")
    List<Request> findAllWithClientOrderByCreatedAtDesc();
}