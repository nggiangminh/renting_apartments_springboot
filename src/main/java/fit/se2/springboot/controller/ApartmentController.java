package fit.se2.springboot.controller;

import fit.se2.springboot.model.Apartment;
import fit.se2.springboot.service.ApartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value= "/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    // List all apartments
    @GetMapping("/")
    public String listApartments(Model model) {
        model.addAttribute("apartments", apartmentService.findAll());
        return "apartmentsList";
    }

    // Display one apartment by ID
    @GetMapping("/{id}")
    public String getApartmentById(@PathVariable Long id, Model model) {
        Apartment apartment = apartmentService.getApartmentById(id);
        model.addAttribute("apartment", apartment);
        return "apartmentDetails";  // apartment-details.html
    }

    // Display form to add a new apartment
    @GetMapping("/new")
    public String newApartmentForm(Model model) {
        model.addAttribute("apartment", new Apartment());
        return "apartmentAdd";  // add-apartment-form.html
    }

    // Process the form to add a new apartment
    @PostMapping("/")
    public String addApartment(@ModelAttribute Apartment apartment) {
        apartmentService.saveApartment(apartment);
        return "redirect:/apartments/";  // Redirect after post
    }
}

