package com.autoservice.controller;

import com.autoservice.model.Request;
import com.autoservice.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("requests", requestService.getAllRequestsWithClient());
        model.addAttribute("statuses", requestService.getAllStatuses());
        return "employee/dashboard";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long requestId, @RequestParam Integer statusId) {
        requestService.updateStatus(requestId, statusId);
        return "redirect:/employee/dashboard";
    }

    @PostMapping("/delete")
    public String deleteRequest(@RequestParam Long requestId) {
        requestService.deleteRequest(requestId);
        return "redirect:/employee/dashboard";
    }

    // === Новые методы для редактирования ===

    // Отображение формы редактирования
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Request request = requestService.getRequestById(id);
        if (request == null) {
            return "redirect:/employee/dashboard";
        }
        model.addAttribute("request", request);
        model.addAttribute("statuses", requestService.getAllStatuses());
        return "employee/edit-request";
    }

    // Сохранение изменений после редактирования
    @PostMapping("/edit")
    public String updateRequest(@ModelAttribute Request updatedRequest,
                                @RequestParam Long id,
                                @RequestParam Integer statusId) {
        Request existing = requestService.getRequestById(id);
        if (existing != null) {
            // Обновляем поля
            existing.setRegNumber(updatedRequest.getRegNumber());
            existing.setCarMake(updatedRequest.getCarMake());
            existing.setCarModel(updatedRequest.getCarModel());
            existing.setProblemType(updatedRequest.getProblemType());
            existing.setProblemDescription(updatedRequest.getProblemDescription());
            existing.setPhone(updatedRequest.getPhone());
            existing.setEmail(updatedRequest.getEmail());
            existing.setVisitDate(updatedRequest.getVisitDate());
            // Обновляем статус
            existing.setStatus(requestService.getStatusById(statusId));
            requestService.updateRequest(existing);
        }
        return "redirect:/employee/dashboard";
    }
}