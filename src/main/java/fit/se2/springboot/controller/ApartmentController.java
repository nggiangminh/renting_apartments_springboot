package fit.se2.springboot.controller;

import fit.se2.springboot.model.Apartment;
import fit.se2.springboot.repository.ApartmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class ApartmentController {
    @Autowired
    ApartmentRepository apartmentRepository;


    @RequestMapping(value = "/home")
    public String homePage(Model model) {
        return "home";
    }
    @RequestMapping(value = "/login")
    public String loginPage(Model model) {
        return "logIn";
    }

    @RequestMapping(value = "/signup")
    public String signupPage(Model model) {
        return "signup";
    }

    @RequestMapping(value = "/manage")
    public String getAllApartment(Model model){
        List<Apartment> apartments = apartmentRepository.findAll();
        model.addAttribute("apartments", apartments);
        return "manageApartment";


    }    @RequestMapping(value = "/apartment")
    public String AllApartment(Model model){
        List<Apartment> apartments = apartmentRepository.findAll();
        model.addAttribute("apartments", apartments);
        return "apartmentList";
    }

    @RequestMapping(value = "/detail/{id}")
    public String getApartmentByID(@PathVariable (value="id") Long id , Model model){
        Apartment apartment = apartmentRepository.getById(id);
        model.addAttribute("apartment", apartment);
        return "apartmentDetail";
    }


    @RequestMapping(value = "/update/{id}")
    public String updateApartment(@PathVariable (value="id") Long id , Model model){
        Apartment apartment = apartmentRepository.getById(id);
        model.addAttribute("apartment", apartment);
        return "apartmentUpdate";
    }

    @RequestMapping(value = "/save")
    public String saveUpdate(@Valid Apartment apartment, BindingResult result){
       if (result.hasErrors()){
           return "apartmentUpdate";
       }
       else {
            apartmentRepository.save(apartment);
            return "redirect:/update/" + apartment.getId();
        }
    }


    @RequestMapping(value ="/add")
    public String addEmployee(Model model) {
        Apartment apartment = new Apartment();
        model.addAttribute("apartment" , apartment);
        return "apartmentAdd";
    }

    @RequestMapping(value = "/insert")
    public String insertEmployee(@Valid Apartment apartment, BindingResult result){
        if (result.hasErrors()){
            return "apartmentAdd";
        }
        else{
            apartmentRepository.save(apartment);
            return "redirect:/detail/" + apartment.getId();
        }

    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteApartment(@PathVariable(value = "id") Long id){
        if(apartmentRepository.findById(id).isPresent()){
            Apartment apartment = apartmentRepository.findById(id).get();

        }
        return "redirect:/" ;
    }
}
