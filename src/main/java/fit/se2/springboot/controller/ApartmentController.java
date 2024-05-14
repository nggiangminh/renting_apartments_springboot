package fit.se2.springboot.controller;

import fit.se2.springboot.model.Apartment;
import fit.se2.springboot.model.CustomUserDetails;
import fit.se2.springboot.model.User;
import fit.se2.springboot.service.ApartmentService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    // List all apartments
    @GetMapping("")
    public String listApartments(Model model) {
        List<Apartment> apartments = apartmentService.findAll();
        model.addAttribute("apartments", apartments);
        return "apartmentList";
    }

    // Display one apartment by ID
    @GetMapping("/{id}")
    public String getApartmentById(@PathVariable Long id, Model model) {
        Apartment apartment = apartmentService.getApartmentById(id);
        model.addAttribute("apartment", apartment);
        return "apartmentDetails";  // apartment-details.html
    }

    // Display form to add a new apartment
    @GetMapping("/add")
    public String newApartmentForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName()))
            return "redirect:/login";
        model.addAttribute("apartment", new Apartment());
        return "apartmentAdd";
    }

    // Process the form to add a new apartment
    @PostMapping("/insert")
    public String addApartment(@Valid @ModelAttribute Apartment apartment, BindingResult result) {
        if (result.hasErrors()) {
            return "apartmentAdd";
        }

        // Fetch the current user's ID from the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        // Set the current user's ID as the owner ID of the apartment
        apartment.setOwner(currentUser);

        // Save the apartment with the owner ID
        apartmentService.saveApartment(apartment);

        return "redirect:/apartment";
    }

    // Update an existing apartment
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Apartment apartment = apartmentService.getApartmentById(id);
        if (apartment == null) {
            return "redirect:/apartment/";
        }

        // Check if current user is the owner of the apartment
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId(); // Your UserDetails should have a method to get the user ID

        if (!apartment.getOwner().getId().equals(currentUserId)) {
            return "redirect:/apartment/"; // Redirect them away if they're not the owner
        }

        model.addAttribute("apartment", apartment);
        return "apartmentUpdate";
    }

    @PostMapping("/save/{id}")
    public String updateApartment(@PathVariable Long id, @Valid @ModelAttribute Apartment apartment, BindingResult result) {
        if (result.hasErrors()) {
            return "apartmentUpdate";
        }

        // Additional security check to ensure the logged-in user is the owner
        Apartment existingApartment = apartmentService.getApartmentById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        if (!existingApartment.getOwner().getId().equals(currentUserId)) {
            return "redirect:/apartment/"; // Redirect if not the owner
        }

        apartmentService.updateApartment(id, apartment);
        return "redirect:/apartment/" + id;
    }

    // Delete an apartment
    @GetMapping("/delete/{id}")
    public String deleteApartment(@PathVariable Long id) {
        Apartment apartment = apartmentService.getApartmentById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        if (!apartment.getOwner().getId().equals(currentUserId)) {
            return "redirect:/apartment"; // Redirect if not the owner
        }

        apartmentService.deleteApartment(id);
        return "redirect:/apartment";
    }
    @GetMapping("/detail/{id}")
    public String showApartmentDetails(@PathVariable Long id, Model model) {
        Apartment apartment = apartmentService.getApartmentById(id);
        model.addAttribute("apartment", apartment);
        return "apartmentDetail";
    }
}
