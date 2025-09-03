package fit.se2.springboot.service;

import fit.se2.springboot.model.Apartment;
import fit.se2.springboot.model.Wishlist;
import fit.se2.springboot.repository.ApartmentRepository;
import fit.se2.springboot.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;


    public Wishlist getWishlistByUserId(Long userId) {

        return wishlistRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wishlist not found for user ID: " + userId));
    }
    public void addApartmentToWishlist(Long userId, Long apartmentId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));
        wishlist.getApartments().add(apartment);
        wishlistRepository.save(wishlist);
    }
    public void removeApartmentFromWishlist(Long userId, Long apartmentId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));
        wishlist.getApartments().remove(apartment);
        wishlistRepository.save(wishlist);
    }
}
