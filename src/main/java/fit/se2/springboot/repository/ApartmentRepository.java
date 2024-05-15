package fit.se2.springboot.repository;

import fit.se2.springboot.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    // Find all apartments by user ID
    List<Apartment> findByOwnerId(Long ownerId);

    // Find all except apartments by user ID
    List<Apartment> findAllByOwnerIdNot(Long ownerId);
}

