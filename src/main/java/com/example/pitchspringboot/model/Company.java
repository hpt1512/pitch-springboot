package com.example.pitchspringboot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "`name`", nullable = false, columnDefinition = "varchar(100)")
    @NotBlank(message = "Không được để trống")
    private String name;
    @Column(name = "image", columnDefinition = "varchar(45)")
    private String image;
    @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(12)")
    @NotBlank(message = "Không được để trống")
    private String phoneNumber;
    @Column(name = "email", columnDefinition = "varchar(100)")
    @NotBlank(message = "Không được để trống")
    private String email;
    @Column(name = "address", nullable = false, columnDefinition = "varchar(100)")
    @NotBlank(message = "Không được để trống")
    private String address;
    @Column(name = "map_url", columnDefinition = "varchar(800)")
    private String mapUrl;
    @ManyToOne
    @JoinColumn(name = "id_location", nullable = false)
    private Location location;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
//    @NotNull(message = "Không được để trống")
    private User user;
    @Column(name = "datetime_created")
    private String dateTimeCreated;

    public Company() {
    }

    public Company(Integer id, String name, String image, String phoneNumber, String email, String address, String mapUrl, Location location, User user) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.mapUrl = mapUrl;
        this.location = location;
        this.user = user;
    }

    public Company(Integer id, String name, String image, String phoneNumber, String email, String address, String mapUrl, Location location, User user, String dateTimeCreated) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.mapUrl = mapUrl;
        this.location = location;
        this.user = user;
        this.dateTimeCreated = dateTimeCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }
}
