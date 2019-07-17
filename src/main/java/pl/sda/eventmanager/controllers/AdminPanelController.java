package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.services.AdminPanelUsersService;

@Controller
public class AdminPanelController {

    private final AdminPanelUsersService adminPanelUsersService;

    public AdminPanelController(AdminPanelUsersService adminPanelUsersService) {
        this.adminPanelUsersService = adminPanelUsersService;
    }

    @GetMapping("adminpanelusers")
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView("adminpanelusers");
        modelAndView.addObject("allUsers", adminPanelUsersService.findAll());
        return modelAndView;
    }
}
