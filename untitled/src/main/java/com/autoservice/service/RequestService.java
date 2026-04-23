package com.autoservice.service;

import com.autoservice.model.Request;
import com.autoservice.model.Status;
import com.autoservice.model.User;
import com.autoservice.repository.RequestRepository;
import com.autoservice.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService {
    @Autowired private RequestRepository requestRepository;
    @Autowired private StatusRepository statusRepository;

    public void createRequest(Request request, User client) {
        request.setClient(client);
        Status defaultStatus = statusRepository.findByName("В обработке")
                .orElseThrow(() -> new RuntimeException("Статус не найден"));
        request.setStatus(defaultStatus);
        request.setCreatedAt(LocalDateTime.now());
        requestRepository.save(request);
    }

    public Request getRequestById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    public void updateRequest(Request request) {
        requestRepository.save(request);
    }

    public Status getStatusById(Integer id) {
        return statusRepository.findById(id).orElse(null);
    }

    public List<Request> getRequestsByClient(User client) {
        return requestRepository.findByClientOrderByCreatedAtDesc(client);
    }

    public List<Request> getAllRequestsWithClient() {
        return requestRepository.findAllWithClientOrderByCreatedAtDesc();
    }

    public void updateStatus(Long requestId, Integer statusId) {
        Request request = requestRepository.findById(requestId).orElseThrow();
        Status status = statusRepository.findById(statusId).orElseThrow();
        request.setStatus(status);
        requestRepository.save(request);
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }
}