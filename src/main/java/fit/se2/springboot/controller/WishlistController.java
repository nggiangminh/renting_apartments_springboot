package fit.se2.springboot.controller;

import fit.se2.springboot.model.CustomUserDetails;
import fit.se2.springboot.model.Wishlist;
import fit.se2.springboot.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("")
    public String getMyWishlist(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Redirect to login if not authenticated or recognized as "anonymousUser"
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        Wishlist wishlist = wishlistService.getWishlistByUserId(userId);

        model.addAttribute("wishlist", wishlist);
        return "wishlist"; // Return the Thymeleaf template name
    }

    @PostMapping("/add/{apartmentId}")
    public ResponseEntity<?> addApartmentToWishlist(@PathVariable Long apartmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName()))
            showLoginPage();
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        wishlistService.addApartmentToWishlist(userId, apartmentId);
        return ResponseEntity.ok("Added to wishlist");
    }

    @PostMapping("/remove/{apartmentId}")
    public ResponseEntity<?> removeApartmentFromWishlist(@PathVariable Long apartmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName()))
            showLoginPage();
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        wishlistService.removeApartmentFromWishlist(userId, apartmentId);
        return ResponseEntity.ok("Removed from wishlist");
    }

    public String showLoginPage() {
        return "redirect:/login";
    }
}
