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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     String id;
     String username;
     String password;
     String firstName;
     String lastName;
     LocalDate birthDay;
     String email;
     String  phone;
     @ManyToMany
     Set<Role> role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<RealEstate> realEstates;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    Set<Appointment> buyerAppointments;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    Set<Appointment> sellerAppointments;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    Set<Transaction> sellerTransaction;
    boolean rule = false;
}
