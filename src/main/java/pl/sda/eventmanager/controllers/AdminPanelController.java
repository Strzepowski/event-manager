package pl.sda.eventmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.services.AdminPanelService;

@Controller
public class AdminPanelController {

    private final AdminPanelService adminPanelService;

    public AdminPanelController(AdminPanelService adminPanelService) {
        this.adminPanelService = adminPanelService;
    }

    @GetMapping("adminpanel")
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView("adminpanel");
        modelAndView.addObject("allUsers", adminPanelService.findAll());
        return modelAndView;
    }
}
