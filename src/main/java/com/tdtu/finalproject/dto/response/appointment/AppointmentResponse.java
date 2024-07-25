package com.tdtu.finalproject.dto.response.appointment;

import com.tdtu.finalproject.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AppointmentResponse {
    Long id;
    String nameBuyer;
    String nameSeller;
    String phoneBuyer;
    String nameRealEstate;
    LocalDate appointmentDateTime;
    String status;
    String note;

    // Phương thức để thiết lập thông tin của người mua và người bán
}
