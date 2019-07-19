package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.services.AdminOperationsService;
import pl.sda.eventmanager.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private final AdminOperationsService adminOperationsService;
    private final UserService userService;

    public AdminPanelController(AdminOperationsService adminOperationsService, UserService userService) {
        this.adminOperationsService = adminOperationsService;
        this.userService = userService;
    }

    @GetMapping("adminpanelusers")
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView("adminpanelusers");
        modelAndView.addObject("allUsers", userService.findAll());
        return modelAndView;
    }
}
