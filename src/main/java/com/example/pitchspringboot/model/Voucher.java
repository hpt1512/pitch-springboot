package com.example.pitchspringboot.model;

import javax.persistence.*;

@Entity(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "`name`", nullable = false, columnDefinition = "varchar(45)")
    private String name;
    @Column(name = "`description`", columnDefinition = "varchar(100)")
    private String description;
    @Column(name = "bonus", nullable = false)
    private Integer bonus;

    public Voucher() {
    }

    public Voucher(Integer id, String name, String description, Integer bonus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bonus = bonus;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
}
