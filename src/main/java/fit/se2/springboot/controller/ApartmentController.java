package fit.se2.springboot.controller;

import fit.se2.springboot.model.Apartment;
import fit.se2.springboot.service.ApartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("apartment", new Apartment());
        return "apartmentAdd";  // add-apartment-form.html
    }

    // Process the form to add a new apartment
    @PostMapping("/insert")
    public String addApartment(@Valid @ModelAttribute Apartment apartment, BindingResult result) {
        if (result.hasErrors()) {
            return "apartmentList";
        }
        apartmentService.saveApartment(apartment);
        return "redirect:/apartment/";
    }

    // Update an existing apartment
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Apartment apartment = apartmentService.getApartmentById(id);
        if (apartment == null) {
            return "redirect:/apartment/";
        }
        model.addAttribute("apartment", apartment);
        return "apartmentUpdate";
    }

    @PostMapping("/save")
    public String updateApartment(@PathVariable Long id, @Valid @ModelAttribute Apartment apartment, BindingResult result) {
        if (result.hasErrors()) {
            return "apartmentUpdate";
        }
        apartmentService.updateApartment(id, apartment);
        return "redirect:/apartment/" + id;
    }

    // Delete an apartment
    @GetMapping("/delete/{id}")
    public String deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return "redirect:/apartment/";
    }
}
