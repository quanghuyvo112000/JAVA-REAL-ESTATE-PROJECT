package com.tdtu.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    User seller;

    @ManyToOne
    @JoinColumn(name = "real_estate_id", nullable = false)
    RealEstate realEstate;

    LocalDate appointmentDateTime;

    String nameBuyer;

    String nameSeller;

    String phoneBuyer;

    String nameRealEstate;

    String status = "WAITED";

    String note;

}