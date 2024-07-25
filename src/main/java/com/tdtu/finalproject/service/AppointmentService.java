package com.tdtu.finalproject.service;

import com.tdtu.finalproject.dto.request.appointment.AppointmentRequest;
import com.tdtu.finalproject.dto.response.appointment.AppointmentResponse;
import com.tdtu.finalproject.entity.Appointment;
import com.tdtu.finalproject.entity.RealEstate;
import com.tdtu.finalproject.entity.User;
import com.tdtu.finalproject.mapper.AppointmentMapper;
import com.tdtu.finalproject.repository.AppointmentRepository;
import com.tdtu.finalproject.repository.RealEstateRepository;
import com.tdtu.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final RealEstateRepository realEstateRepository;
    private final AppointmentMapper appointmentMapper;
    @Autowired
    private final EmailService emailService;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        RealEstate realEstate = realEstateRepository.findById(request.getRealEstateId())
                .orElseThrow(() -> new RuntimeException("Real estate not found"));

        String userId = realEstate.getUser().getId();
        String LastNameSeller = realEstate.getUser().getLastName();
        String FirstNameSeller = realEstate.getUser().getFirstName();

        String LastNameBuyer = buyer.getLastName();
        String FirstNameBuyer = buyer.getFirstName();

        String fullnameBuyer = LastNameBuyer + " " + FirstNameBuyer;

        String fullnameSeller = LastNameSeller + " " + FirstNameSeller;

        String emailSeller = realEstate.getUser().getEmail();


        String phoneBuyer = realEstate.getUser().getPhone();

        String nameRealEstate = realEstate.getTitle().toUpperCase();

        emailService.sendSimpleMessage(emailSeller, "THÔNG BÁO ĐẶT LỊCH XEM BẤT ĐỘNG SẢN TÊN " + nameRealEstate, "Tin tuyệt vời! Khách hàng đã chốt lịch hẹn đi xem dự án " + nameRealEstate + "vào ngày " + request.getAppointmentTime() + "\n\n" +
                "Khách hàng đã xác nhận lịch hẹn qua email này và rất háo hức được trải nghiệm trực tiếp dự án.");

        User seller = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Appointment appointment = appointmentMapper.fromRequest(request, buyer, seller, realEstate);
        appointment.setStatus("WAITED");
        appointment.setAppointmentDateTime(request.getAppointmentTime());
        appointment.setNameSeller(fullnameSeller);
        appointment.setNameBuyer(fullnameBuyer);
        appointment.setPhoneBuyer(phoneBuyer);
        appointment.setNameRealEstate(nameRealEstate);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(savedAppointment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<AppointmentResponse> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Phương thức lấy tất cả các cuộc hẹn theo buyer_id
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<AppointmentResponse> getAllAppointmentsByBuyerId(String buyerId) {
        List<Appointment> appointments = appointmentRepository.findByBuyerId(buyerId);
        return appointments.stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Phương thức lấy tất cả các cuộc hẹn theo seller_id
    @PreAuthorize("hasRole('BROKER')")
    public List<AppointmentResponse> getAllAppointmentsBySellerId(String sellerId) {
        List<Appointment> appointments = appointmentRepository.findBySellerId(sellerId);
        return appointments.stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('BROKER')")
    public void confirmAppointmentSeller(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        LocalDate date = appointment.getAppointmentDateTime();
        String idBuyer = appointment.getBuyer().getId();
        User user = userRepository.findById(idBuyer)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        String emailBuyer = user.getEmail();
        emailService.sendSimpleMessage(emailBuyer, "THÔNG BÁO XÁC NHẬN LỊCH HẸN ", "Cảm ơn bạn đã đặt lịch hẹn xem nhà vào ngày " + date + "\n" +
                "Chúng tôi rất vui khi được chào đón bạn và mong muốn được giới thiệu trực tiếp dự án với bạn. \n\n" +
                "Đội ngũ nhân viên chuyên nghiệp của chúng tôi sẽ có mặt tại địa điểm xem nhà vào đúng giờ hẹn để hỗ trợ bạn một cách tốt nhất.");

        // Cập nhật trạng thái của cuộc hẹn thành "CONFIRM"
        appointment.setStatus("CONFIRM");



        // Lưu lại cuộc hẹn đã được cập nhật
        appointmentRepository.save(appointment);
    }
    @PreAuthorize("hasRole('CUSTOMER')")

    public void confirmAppointmentBuyer(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        LocalDate date = appointment.getAppointmentDateTime();
        String idSeller = appointment.getSeller().getId();
        User user = userRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        String emailSeller = user.getEmail();
        emailService.sendSimpleMessage(emailSeller, "HỦY LỊCH HẸN ", "Tôi xin phép hủy lịch hẹn vào ngày " + date + ". Tôi thành thật xin lỗi, tôi " +
                "sẽ liên hệ lại");


        // Cập nhật trạng thái của cuộc hẹn thành "CONFIRM"
        appointment.setStatus("CANCEL");


        // Lưu lại cuộc hẹn đã được cập nhật
        appointmentRepository.save(appointment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        String nameRealEstate = appointment.getNameRealEstate();

        String idSeller = appointment.getSeller().getId();
        User user = userRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        String emailSeller = user.getEmail();
        emailService.sendSimpleMessage(emailSeller, "THÔNG BÁO XÓA LỊCH HẸN XEM BẤT ĐỘNG SẢN " + nameRealEstate, "ADMIN thông báo xóa lịch hẹn xem bất động sản. Xin cảm ơn");
        appointmentRepository.deleteById(appointmentId);
    }
}
