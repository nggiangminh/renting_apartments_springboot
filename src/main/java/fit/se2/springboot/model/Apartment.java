package fit.se2.springboot.model;

import jakarta.persistence.*;
import java.util.Date;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "apartment")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

//    @Column(nullable = false)
//    private boolean isAvailable = true;


    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String image; // This could be a JSON string or comma-separated values

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String address;

    @Column(length = 5000)
    private String amenities;

    @Column(length = 5000)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate = new Date();

    // Constructors
    public Apartment() {
    }

    // Getters and Setters

    @Length(min = 3, max = 30)
    private String name;

//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public void setAvailable(boolean isAvailable) {
//        this.isAvailable = isAvailable;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }
}