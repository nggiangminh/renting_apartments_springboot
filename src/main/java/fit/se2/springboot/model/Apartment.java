package fit.se2.springboot.model;


import jakarta.persistence.*;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , nullable = false)
    private Long id;
    private String name;
    private double price;
    private String image;
    private String address;
}
