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
    public List<Apartment> findAllExcludingUserOrderByPriceAsc(Long userId) {
        return apartmentRepository.findAllByOwnerIdNotOrderByPriceAsc(userId);
    }

    public List<Apartment> findAllExcludingUserOrderByPriceDesc(Long userId) {
        return apartmentRepository.findAllByOwnerIdNotOrderByPriceDesc(userId);
    }

    public List<Apartment> findAllExcludingUserOrderByAreaAsc(Long userId) {
        return apartmentRepository.findAllByOwnerIdNotOrderByAreaAsc(userId);
    }

    public List<Apartment> findAllExcludingUserOrderByAreaDesc(Long userId) {
        return apartmentRepository.findAllByOwnerIdNotOrderByAreaDesc(userId);
    }

    public List<Apartment> findAllOrderByPriceAsc() {
        return apartmentRepository.findAllByOrderByPriceAsc();
    }

    public List<Apartment> findAllOrderByPriceDesc() {
        return apartmentRepository.findAllByOrderByPriceDesc();
    }

    public List<Apartment> findAllOrderByAreaAsc() {
        return apartmentRepository.findAllByOrderByAreaAsc();
    }

    public List<Apartment> findAllOrderByAreaDesc() {
        return apartmentRepository.findAllByOrderByAreaDesc();
    }
    // Find all apartments
    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    // Find all excluding current user's apartments
    public List<Apartment> findAllExcludingUser(Long userId) {
        // Exclude apartments based on the owner's ID
        return apartmentRepository.findAllByOwnerIdNot(userId);
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

    // Find apartments by user's ID
    public List<Apartment> getApartmentsByUserId(Long userId) {
        return apartmentRepository.findByOwnerId(userId);
    }

    // Save an apartment
    public Apartment saveApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    // Update an apartment
    public Apartment updateApartment(Long id, Apartment apartmentDetails) {
        Apartment apartment = getApartmentById(id);
        apartment.setTitle(apartmentDetails.getTitle());
        apartment.setAvailable(apartmentDetails.isAvailable());
        apartment.setArea(apartmentDetails.getArea());
        apartment.setPrice(apartmentDetails.getPrice());
        apartment.setAddress(apartmentDetails.getAddress());
        apartment.setDescription(apartmentDetails.getDescription());
        apartment.setAmenities(apartmentDetails.getAmenities());
        return apartmentRepository.save(apartment);
    }

    // Delete an apartment
    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }
}
