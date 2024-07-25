package com.tdtu.finalproject.dto.response.trasaction;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    Long id;
    Double commission;
    Double amount;
    LocalDate createdAt;
    String sellerName;
    String realEstateName;
    String buyerName;
    String buyerEmail;
}
