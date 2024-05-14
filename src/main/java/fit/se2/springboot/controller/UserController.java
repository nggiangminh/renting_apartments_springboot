package fit.se2.springboot.controller;

import fit.se2.springboot.model.Apartment;
import fit.se2.springboot.model.CustomUserDetails;
import fit.se2.springboot.model.User;
import fit.se2.springboot.service.ApartmentService;
import fit.se2.springboot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("")
public class UserController {

    private final UserService userService;
    private final ApartmentService apartmentService;

    public UserController(UserService userService, ApartmentService apartmentService) {
        this.userService = userService;
        this.apartmentService = apartmentService;
    }
    // Display one user by ID
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        List<Apartment> apartments = apartmentService.getApartmentsByUserId(id); // Assuming such a method exists

        model.addAttribute("user", user);
        model.addAttribute("apartments", apartments);
        return "userDetails";
    }
    @GetMapping("/my_profile")
    public String myProfile(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.getUserById(userDetails.getId()); // Fetch user details
        List<Apartment> apartments = apartmentService.getApartmentsByUserId(userDetails.getId()); // Assuming such a method exists

        model.addAttribute("user", user);
        model.addAttribute("apartments", apartments);
        return "myProfile";
    }

    @GetMapping("/edit_my_profile")
    public String showUpdateForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId(); // Fetch the current user's ID

        User user = userService.getUserById(userId); // Fetch existing user by ID
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not found
        }

        model.addAttribute("user", user);
        return "updateProfile";
    }
    @PostMapping("/update_my_profile")
    public String updateMyProfile(@ModelAttribute User updatedUser, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId(); // Fetch the current user's ID

        User existingUser = userService.getUserById(userId); // Fetch existing user by ID
        if (existingUser == null) {
            return "redirect:/login"; // Redirect to login if user is not found
        }

        // Update fields - ensure these are properly validated and consider security implications
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());

        userService.saveUser(existingUser); // Assuming you have a save method in your UserService

        model.addAttribute("user", existingUser);
        return "redirect:/my_profile"; // Redirect back to the profile page to see the changes
    }

}


