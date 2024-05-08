package fit.se2.springboot.repository;

import fit.se2.springboot.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
