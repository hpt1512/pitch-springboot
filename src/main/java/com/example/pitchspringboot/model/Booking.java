package com.example.pitchspringboot.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_pitch", nullable = false)
    private Pitch pitch;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    @Column(name = "`date`", nullable = false)
    private Date date;
    @Column(name = "`time`", nullable = false, columnDefinition = "varchar(12)")
    private String time;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "note", columnDefinition = "varchar(100)")
    private String note;
    @ManyToOne
    @JoinColumn(name = "id_voucher")
    private Voucher voucher;
    @Column(name = "`status`", columnDefinition = "int default 0")
    private int status;

    public Booking() {
    }

    public Booking(int id, Pitch pitch, User user, Date date, String time, int price, String note, Voucher voucher, int status) {
        this.id = id;
        this.pitch = pitch;
        this.user = user;
        this.date = date;
        this.time = time;
        this.price = price;
        this.note = note;
        this.voucher = voucher;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
