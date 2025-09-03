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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String listApartments(@RequestParam(value = "sort", required = false) String sort, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Apartment> apartments;

        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            Long currentUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            switch (sort) {
                case "price_desc":
                    apartments = apartmentService.findAllExcludingUserOrderByPriceDesc(currentUserId);
                    break;
                case "area_asc":
                    apartments = apartmentService.findAllExcludingUserOrderByAreaAsc(currentUserId);
                    break;
                case "area_desc":
                    apartments = apartmentService.findAllExcludingUserOrderByAreaDesc(currentUserId);
                    break;
                default:
                    apartments = apartmentService.findAllExcludingUserOrderByPriceAsc(currentUserId);
            }
        } else {
            switch (sort) {
                case "price_desc":
                    apartments = apartmentService.findAllOrderByPriceDesc();
                    break;
                case "area_asc":
                    apartments = apartmentService.findAllOrderByAreaAsc();
                    break;
                case "area_desc":
                    apartments = apartmentService.findAllOrderByAreaDesc();
                    break;
                default:
                    apartments = apartmentService.findAllOrderByPriceAsc();
            }
        }

        model.addAttribute("apartments", apartments);
        model.addAttribute("sort", sort);
        return "apartmentList";  // View that lists all apartments
    }
    @GetMapping("/my")
    public String getmyApartments(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName()))
            return "redirect:/login";
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId(); // Fetch the current user's ID
        List<Apartment> apartments = apartmentService.getApartmentsByUserId(userId);
        model.addAttribute("apartments", apartments);
        return "myApartment";
    }

    @GetMapping("/my/{id}")
    public String getmyApartmentById(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName()))
            return "redirect:/login";
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId(); // Fetch the current user's ID
        Apartment apartment = apartmentService.getApartmentById(id);
        if (apartment == null) {
            redirectAttributes.addFlashAttribute("error", "Apartment not found.");
            return "redirect:/apartment/my";
        }

        if (!userId.equals(apartment.getOwner().getId())) {
            redirectAttributes.addFlashAttribute("error", "You are not the owner of this apartment.");
            return "redirect:/apartment/list";  // Redirect to a safe and general page
        }
        model.addAttribute("apartment", apartment);
        return "myApartmentDetail";  // apartment-details.html
    }

    // Display one apartment by ID
    @GetMapping("/{id}")
    public String getApartmentById(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Apartment apartment = apartmentService.getApartmentById(id);
        if (apartment == null) {
            redirectAttributes.addFlashAttribute("error", "Apartment not found.");
            return "redirect:/apartment";
        }
        model.addAttribute("apartment", apartment);
        return "apartmentDetail";  // apartment-details.html
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

        return "redirect:/apartment/my";
    }

    // Update an existing apartment
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Apartment apartment = apartmentService.getApartmentById(id);
        if (apartment == null) {
            redirectAttributes.addFlashAttribute("error", "Apartment not found.");
            return "redirect:/apartment/my";
        }

        // Check if current user is the owner of the apartment
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId(); // Your UserDetails should have a method to get the user ID

        if (!apartment.getOwner().getId().equals(currentUserId)) {
            redirectAttributes.addFlashAttribute("error", "You are not the owner of this apartment.");
            return "redirect:/apartment"; // Redirect them away if they're not the owner
        }

        model.addAttribute("apartment", apartment);
        return "apartmentUpdate";
    }

    @PostMapping("/save/{id}")
    public String updateApartment(@PathVariable Long id, @Valid @ModelAttribute Apartment apartment, BindingResult result) {
        if (result.hasErrors()) {
            return "apartmentUpdate";
        }
        apartmentService.updateApartment(id, apartment);
        return "redirect:/apartment/my/" + id;
    }

    // Delete an apartment
    @GetMapping("/delete/{id}")
    public String deleteApartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Apartment apartment = apartmentService.getApartmentById(id);
        if (apartment == null) {
            redirectAttributes.addFlashAttribute("error", "Apartment not found.");
            return "redirect:/apartment/my";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long currentUserId = ((CustomUserDetails) userDetails).getId();

        if (!apartment.getOwner().getId().equals(currentUserId)) {
            redirectAttributes.addFlashAttribute("error", "You are not the owner of this apartment.");
            return "redirect:/apartment"; // Redirect them away if they're not the owner
        }

        apartmentService.deleteApartment(id);
        return "redirect:/apartment/my";
    }
}
