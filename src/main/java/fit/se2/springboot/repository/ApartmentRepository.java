package fit.se2.springboot.repository;

import fit.se2.springboot.model.Apartment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findByOwnerId(Long ownerId);

    List<Apartment> findAllByOwnerIdNot(Long ownerId);


    List<Apartment> findAllByOwnerIdNotOrderByPriceAsc(Long ownerId);

    List<Apartment> findAllByOwnerIdNotOrderByPriceDesc(Long ownerId);

    List<Apartment> findAllByOwnerIdNotOrderByAreaAsc(Long ownerId);

    List<Apartment> findAllByOwnerIdNotOrderByAreaDesc(Long ownerId);

    List<Apartment> findAllByOrderByPriceAsc();

    List<Apartment> findAllByOrderByPriceDesc();

    List<Apartment> findAllByOrderByAreaAsc();

    List<Apartment> findAllByOrderByAreaDesc();
}

