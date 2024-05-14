package fit.se2.springboot.controller;


import fit.se2.springboot.model.ChangePasswordForm;
import fit.se2.springboot.model.CustomUserDetails;
import fit.se2.springboot.model.User;
import fit.se2.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage() {
        return "welcome";
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/process_signup")
    public String processSignup(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "signup_success";
    }
    @GetMapping("/change_password")
    public String showChangePasswordForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName()))
            return "redirect:/login";
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "change_password";
    }
    @PostMapping("/process_change_password")
    public String processChangePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @ModelAttribute("changePasswordForm") ChangePasswordForm form,
                                        Model model) {
        User user = userRepo.findById(userDetails.getId()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // Check if new password and confirm new password match
        if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
            model.addAttribute("error", "New passwords do not match.");
            return "change_password";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
            model.addAttribute("error", "Current password is incorrect.");
            return "change_password";
        }

        // Encode and set the new password
        String encodedNewPassword = passwordEncoder.encode(form.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepo.save(user);

        return "redirect:/my_profile";  // Redirect to the profile page
    }


}
