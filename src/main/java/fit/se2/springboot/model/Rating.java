package fit.se2.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    private int rating;

    @Column(columnDefinition = "TEXT")
    private String review;

    public Long getId() {
        return id;
    }

    public User getTenant() {
        return tenant;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}

