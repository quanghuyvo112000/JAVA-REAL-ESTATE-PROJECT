package com.tdtu.finalproject.mapper;

import com.tdtu.finalproject.dto.request.appointment.AppointmentRequest;
import com.tdtu.finalproject.dto.response.appointment.AppointmentResponse;
import com.tdtu.finalproject.entity.Appointment;
import com.tdtu.finalproject.entity.RealEstate;
import com.tdtu.finalproject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentResponse toResponse(Appointment appointment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Appointment fromRequest(AppointmentRequest request, User buyer, User seller, RealEstate realEstate);
}
