package fit.se2.springboot.service;

import fit.se2.springboot.model.Apartment;
import fit.se2.springboot.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    // Find all apartments
    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    // Find an apartment by ID
    public Apartment getApartmentById(Long id) {
        Optional<Apartment> apartment = apartmentRepository.findById(id);
        if (apartment.isPresent()) {
            return apartment.get();
        } else {
            throw new RuntimeException("Apartment not found for id " + id);
        }
    }

    // Save an apartment
    public Apartment saveApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    // Update an apartment
    public Apartment updateApartment(Long id, Apartment apartmentDetails) {
        Apartment apartment = getApartmentById(id);
        apartment.setTitle(apartmentDetails.getTitle());
        apartment.setStatus(apartmentDetails.getStatus());
        apartment.setArea(apartmentDetails.getArea());
        apartment.setPrice(apartmentDetails.getPrice());
        apartment.setLocation(apartmentDetails.getLocation());
        apartment.setDescription(apartmentDetails.getDescription());
        apartment.setAmenities(apartmentDetails.getAmenities());
        return apartmentRepository.save(apartment);
    }

    // Delete an apartment
    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }
}
