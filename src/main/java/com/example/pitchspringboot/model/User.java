package com.example.pitchspringboot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username", nullable = false, columnDefinition = "varchar(15)")
    @NotBlank(message = "{notempty}")
    private String username;
    @Column(name = "`password`", nullable = false, columnDefinition = "varchar(45)")
    private String password;
    @Column(name = "full_name", nullable = false, columnDefinition = "varchar(100)")
    @NotBlank(message = "{notempty}")
    private String fullName;
    @Column(name = "image", columnDefinition = "varchar(45)")
    private String image;
    @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(12)")
    @NotBlank(message = "{notempty}")
    private String phoneNumber;
    @Column(name = "email", columnDefinition = "varchar(100)")
    private String email;
    @Column(name = "address", columnDefinition = "varchar(100)")
    private String address;
    @Column(name = "`point`", columnDefinition = "int default 0")
    private Integer point;
    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    public User() {
    }

    public User(Integer id, String username, String password, String fullName, String image, String phoneNumber, String email, String address, Integer point, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.point = point;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
