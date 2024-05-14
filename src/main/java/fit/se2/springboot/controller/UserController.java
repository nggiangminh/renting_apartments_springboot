//package fit.se2.springboot.controller;
//
//import fit.se2.springboot.model.User;
//import fit.se2.springboot.service.UserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//
//@Controller
//@RequestMapping("/users")
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // Display all users
//    @GetMapping("/")
//    public String listUsers(Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "users";  // HTML page name in src/main/resources/templates
//    }
//
//    // Display one user by ID
//    @GetMapping("/{id}")
//    public String getUserById(@PathVariable Long id, Model model) {
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        return "user-profile";  // user-profile.html
//    }
//
//    // Display the form for adding a new user
//    @GetMapping("/new")
//    public String newUserForm(Model model) {
//        model.addAttribute("user", new User());
//        return "add-user-form";  // add-user-form.html
//    }
//
//    // Process the form for adding a new user
//    @PostMapping("/")
//    public String addUser(@ModelAttribute User user) {
//        userService.saveUser(user);
//        return "redirect:/users/";  // Redirect to list all users
//    }
//}
//
//
