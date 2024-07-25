package com.tdtu.finalproject.controller;

import com.tdtu.finalproject.dto.request.ApiResponse;
import com.tdtu.finalproject.dto.request.appointment.AppointmentRequest;
import com.tdtu.finalproject.dto.response.appointment.AppointmentResponse;
import com.tdtu.finalproject.service.AppointmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {

    AppointmentService appointmentService;

    @PostMapping
    ApiResponse<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
        var result = appointmentService.createAppointment(request);
        return ApiResponse.<AppointmentResponse>builder()
                .result(result)
                .build();
    }


    @GetMapping
    ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        var result = appointmentService.getAllAppointments();
        return ApiResponse.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @GetMapping("/seller/{sellerId}")
    ApiResponse<List<AppointmentResponse>> getAllAppointmentsBySellerId(@PathVariable String sellerId) {
        var result = appointmentService.getAllAppointmentsBySellerId(sellerId);
        return ApiResponse.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @GetMapping("/buyer/{buyerId}")
    ApiResponse<List<AppointmentResponse>> getAllAppointmentsByBuyerId(@PathVariable String buyerId) {
        var result = appointmentService.getAllAppointmentsByBuyerId(buyerId);
        return ApiResponse.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @PutMapping("/seller/{appointmentId}")
    ApiResponse<String> confirmAppointmentSeller(@PathVariable Long appointmentId) {
        appointmentService.confirmAppointmentSeller(appointmentId);
        return ApiResponse.<String>builder()
                .result("Appointment confirmed by seller.")
                .build();
    }

    @PutMapping("/buyer/{appointmentId}")
    ApiResponse<String> confirmAppointmentBuyer(@PathVariable Long appointmentId) {
        appointmentService.confirmAppointmentBuyer(appointmentId);
        return ApiResponse.<String>builder()
                .result("Appointment cancel by buyer.")
                .build();
    }

    @DeleteMapping("/{appointmentId}")
    ApiResponse<String> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ApiResponse.<String>builder()
                .result("Appointment deleted successfully.")
                .build();
    }
}
