package uz.pdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/text")
    public ModelAndView text() {
        ModelAndView modelAndView = new ModelAndView("text");
        modelAndView.addObject("username", "Ali Valiyev");
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("username", "Ali Valiyev");
        return modelAndView;
    }

}
