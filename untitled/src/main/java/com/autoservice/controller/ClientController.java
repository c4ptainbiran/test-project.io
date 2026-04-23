package com.autoservice.controller;

import com.autoservice.model.Request;
import com.autoservice.model.User;
import com.autoservice.service.RequestService;
import com.autoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired private RequestService requestService;
    @Autowired private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        User client = userService.findByLogin(auth.getName());
        model.addAttribute("client", client);
        model.addAttribute("requests", requestService.getRequestsByClient(client));
        return "client/profile";
    }

    @GetMapping("/create-request")
    public String createRequestForm() {
        return "client/create-request";
    }

    @PostMapping("/create-request")
    public String createRequest(@RequestParam String regNumber,
                                @RequestParam String carMake,
                                @RequestParam String carModel,
                                @RequestParam String problemType,
                                @RequestParam(required = false) String problemDescription,
                                @RequestParam String phone,
                                @RequestParam(required = false) String email,
                                @RequestParam String visitDate,
                                Authentication auth) {
        User client = userService.findByLogin(auth.getName());
        Request request = new Request();
        request.setRegNumber(regNumber);
        request.setCarMake(carMake);
        request.setCarModel(carModel);
        request.setProblemType(problemType);
        request.setProblemDescription(problemDescription);
        request.setPhone(phone);
        request.setEmail(email);
        request.setVisitDate(LocalDate.parse(visitDate));
        requestService.createRequest(request, client);
        return "redirect:/client/profile";
    }
}