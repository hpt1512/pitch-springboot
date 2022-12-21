package com.example.pitchspringboot.model;

import javax.persistence.*;

@Entity(name = "pitch")
public class Pitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "`name`", nullable = false, columnDefinition = "varchar(45)")
    private String name;
    @Column(name = "people_max", nullable = false)
    private Integer peopleMax;
    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "`status`", columnDefinition = "int default 0")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    private Company company;

    public Pitch() {
    }

    public Pitch(Integer id, String name, Integer peopleMax, Integer price, Integer status, Company company) {
        this.id = id;
        this.name = name;
        this.peopleMax = peopleMax;
        this.price = price;
        this.status = status;
        this.company = company;
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

    public Integer getPeopleMax() {
        return peopleMax;
    }

    public void setPeopleMax(Integer peopleMax) {
        this.peopleMax = peopleMax;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
