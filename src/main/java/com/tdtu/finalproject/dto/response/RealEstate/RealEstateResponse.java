package com.tdtu.finalproject.dto.response.RealEstate;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RealEstateResponse {
    Long id;
    String title;
    String description;
    String location;
    String address;
    Integer rooms;
    Integer bathrooms;
    String propertyType;
    Double price;
    LocalDate createdAt;
    String role; // role: agent or owner
    String fullname;
    String email;
    String phone;
    String img;
    boolean status;

}
