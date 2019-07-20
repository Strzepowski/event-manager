package pl.sda.eventmanager.controllers.security;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.eventmanager.dto.LoginForm;
import pl.sda.eventmanager.services.validation.AdminValidator;
import pl.sda.eventmanager.services.validation.LoginValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final LoginValidator loginValidator;
    private final AdminValidator adminValidator;

    public LoginController(LoginValidator loginValidator, AdminValidator adminValidator) {
        this.loginValidator = loginValidator;
        this.adminValidator = adminValidator;
    }

    @GetMapping("login")
    public ModelAndView loginGet(){return new ModelAndView("login", "loginForm", new LoginForm());}


    @GetMapping("loginForm")
    public ModelAndView loginFormGet() {
        return new ModelAndView("login", "loginForm", new LoginForm());
    }

    //TODO NAVBAR BUTTONS DISAPPEARING
    @PostMapping("loginForm")
    public ModelAndView loginFormPost(@ModelAttribute LoginForm loginForm, BindingResult bindingResult/*, HttpServletRequest httpServletRequest*/) /*throws ServletException*/ {

        if(loginForm.getEmail().equals("admin")){
        adminValidator.validate(loginForm, bindingResult);
        } else loginValidator.validate(loginForm, bindingResult);

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            return modelAndView;
        }
//        try {
//            httpServletRequest.login(loginForm.getEmail(), loginForm.getPassword());
//        } catch (ServletException e) {
//
//        }
        return new ModelAndView("redirect:/login");
    }
}
