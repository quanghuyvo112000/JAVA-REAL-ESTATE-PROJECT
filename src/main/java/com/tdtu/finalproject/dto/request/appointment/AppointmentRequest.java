package com.tdtu.finalproject.dto.request.appointment;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AppointmentRequest {
    Long realEstateId;
    String buyerId;
    LocalDate appointmentTime;
    String note;
}
