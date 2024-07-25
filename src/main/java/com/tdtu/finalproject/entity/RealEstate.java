package com.tdtu.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
     String title;
    @Lob
    @Column(columnDefinition = "TEXT")
     String description;
     String location;
     String address;
     Integer rooms;
     Integer bathrooms;
     String propertyType;
    @Column(nullable = false)
     Double price;
     LocalDate createdAt;

    @Lob
    @Column(columnDefinition = "TEXT")
     String img;
    //role: agent or owner
    String role;
    String fullname;
    String email;
    String  phone;
    boolean status = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "realEstate", cascade = CascadeType.ALL)
    Set<Appointment> appointments;

    @OneToMany(mappedBy = "realEstate", cascade = CascadeType.ALL)
    Set<Transaction> transactions;

}
