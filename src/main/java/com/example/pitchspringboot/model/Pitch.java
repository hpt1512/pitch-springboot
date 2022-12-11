package com.example.pitchspringboot.model;

import javax.persistence.*;

@Entity(name = "pitch")
public class Pitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "`name`", nullable = false, columnDefinition = "varchar(45)")
    private String name;
    @Column(name = "people_max", nullable = false)
    private int peopleMax;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "`status`", columnDefinition = "int default 0")
    private int status;
    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    private Company company;

    public Pitch() {
    }

    public Pitch(int id, String name, int peopleMax, int price, int status, Company company) {
        this.id = id;
        this.name = name;
        this.peopleMax = peopleMax;
        this.price = price;
        this.status = status;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeopleMax() {
        return peopleMax;
    }

    public void setPeopleMax(int peopleMax) {
        this.peopleMax = peopleMax;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
