package fit.se2.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }
    @RequestMapping("/home")
    public String homePage() {
        return "Home";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
