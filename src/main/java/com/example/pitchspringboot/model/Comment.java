package com.example.pitchspringboot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content")
    @NotBlank
    private String content;
    @Column(name = "likes")
    private Integer likes;
    @Column(name = "datetime_created")
    private String dateTimeCreated;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_company")
    private Company company;

    public Comment() {
    }

    public Comment(Integer id, String content, Integer likes, String dateTimeCreated, User user, Company company) {
        this.id = id;
        this.content = content;
        this.likes = likes;
        this.dateTimeCreated = dateTimeCreated;
        this.user = user;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
