package com.tdtu.finalproject.dto.request.transaction;

import com.tdtu.finalproject.entity.RealEstate;
import com.tdtu.finalproject.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {

    LocalDate createdAt;
    String sellerId;
    Long realEstateId;
    String buyerName;
    String buyerEmail;
}
