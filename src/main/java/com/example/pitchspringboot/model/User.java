package com.example.pitchspringboot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username", nullable = false, columnDefinition = "varchar(15)")
    @NotBlank(message = "Không được để trống")
    @Size(min = 5, max = 15, message = "Tên đăng nhập phải có độ dài từ 5 đến 15 ký tự")
    private String username;
    @Column(name = "`password`", nullable = false, columnDefinition = "varchar(45)")
    @Size(min = 5, message = "Mật khẩu phải có 5 ký tự trở lên")
    private String password;
    @Column(name = "full_name", nullable = false, columnDefinition = "varchar(100)")
    @NotBlank(message = "Không được để trống")
    private String fullName;
    @Column(name = "image", columnDefinition = "varchar(45)")
    private String image;
    @Column(name = "phone_number", nullable = false, columnDefinition = "varchar(12)")
    @NotBlank(message = "Không được để trống")
    private String phoneNumber;
    @Column(name = "email", columnDefinition = "varchar(100)")
    @NotBlank(message = "Không được để trống")
    private String email;
    @Column(name = "address", columnDefinition = "varchar(100)")
    @NotBlank(message = "Không được để trống")
    private String address;
    @Column(name = "`point`", columnDefinition = "int default 0")
    private Integer point;
    @Column(name = "`status`", columnDefinition = "int default 0")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    public User() {
    }

    public User(Integer id, String username, String password, String fullName, String image, String phoneNumber, String email, String address, Integer point, Integer status, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.point = point;
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
