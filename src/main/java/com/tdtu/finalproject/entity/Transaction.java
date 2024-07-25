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
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double commission;
    Double amount;
    LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    User seller;
    @ManyToOne
    @JoinColumn(name = "real_estate_id", nullable = false)
    RealEstate realEstate;
    String sellerName;
    String realEstateName;
    String buyerName;
    String buyerEmail;
}
