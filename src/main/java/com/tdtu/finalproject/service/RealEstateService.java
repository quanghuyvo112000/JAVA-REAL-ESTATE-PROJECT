package com.tdtu.finalproject.service;

import com.tdtu.finalproject.dto.request.realEstate.RealEstateRequest;
import com.tdtu.finalproject.dto.request.realEstate.UpdateRealEstateRequest;
import com.tdtu.finalproject.dto.response.RealEstate.RealEstateResponse;
import com.tdtu.finalproject.dto.response.user.UserResponse;
import com.tdtu.finalproject.entity.RealEstate;
import com.tdtu.finalproject.entity.User;
import com.tdtu.finalproject.exception.AppException;
import com.tdtu.finalproject.exception.ErrorCode;
import com.tdtu.finalproject.mapper.RealEstateMapper;
import com.tdtu.finalproject.repository.RealEstateRepository;
import com.tdtu.finalproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RealEstateService {

    RealEstateRepository realEstateRepository;
    RealEstateMapper realEstateMapper;
    UserRepository userRepository;
    @Autowired
    private final EmailService emailService;
    @PreAuthorize("hasAuthority('CREATE_DATA')")
    public RealEstateResponse addRealEstate(RealEstateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String emailAdmin = "huyquangvo11.2000@gmail.com";

        emailService.sendSimpleMessage(emailAdmin, "THÔNG BÁO CÓ BẤT ĐỘNG SẢN MỚI " + request.getTitle().toUpperCase(), "ADMIN có 1 bất động sản mới được đăng " + request.getTitle().toUpperCase() +", vui lòng phê duyệt" + "\n\n Xin cảm ơn");

        RealEstate realEstate = realEstateMapper.fromRequest(request);
        realEstate.setUser(user);
        RealEstate savedRealEstate = realEstateRepository.save(realEstate);
        return realEstateMapper.toResponse(savedRealEstate);
    }
    public RealEstateResponse updateRealEstate(Long id, UpdateRealEstateRequest request) {
        RealEstate existingRealEstate = realEstateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Real Estate not found"));
        realEstateMapper.updateRealEstate(existingRealEstate, request);
        RealEstate savedRealEstate = realEstateRepository.save(existingRealEstate);
        return realEstateMapper.toResponse(savedRealEstate);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRealEstate(Long id) {
        RealEstate existingRealEstate = realEstateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Real Estate not found"));

        String nameRealEstate = existingRealEstate.getTitle().toUpperCase();

        String idSeller = existingRealEstate.getUser().getId();

        User user = userRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        String emailSeller = user.getEmail();

        emailService.sendSimpleMessage(emailSeller, "THÔNG BÁO XÓA BẤT ĐỘNG SẢN " + nameRealEstate, "ADMIN thông báo xóa bất động sản của bạn đã đăng. Xin cảm ơn");

        realEstateRepository.deleteById(id);
    }

    @PreAuthorize("permitAll()")
    public List<RealEstateResponse> getAllRealEstates() {
        List<RealEstate> realEstates = realEstateRepository.findAll();
        return realEstates.stream()
                .map(realEstateMapper::toResponse)
                .collect(Collectors.toList());
    }

    public RealEstateResponse getRealEstateById(Long id) {
        RealEstate realEstate = realEstateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Real Estate not found"));
        return realEstateMapper.toResponse(realEstate);
    }

     @PreAuthorize("hasAuthority('READ_DATA')")
    public List<RealEstateResponse> getRealEstateByUserId(String userId) {
        List<RealEstate> realEstates = realEstateRepository.findByUserId(userId);
        return realEstates.stream()
                .map(realEstateMapper::toResponse)
                .collect(Collectors.toList());
    }



    @PreAuthorize("hasRole('ADMIN')")
    public void activateRealEstate(Long id) {
        RealEstate realEstate = realEstateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Real Estate not found"));
        realEstate.setStatus(true);

        String nameRealEstate = realEstate.getTitle().toUpperCase();

        String idSeller = realEstate.getUser().getId();

        User user = userRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        String emailSeller = user.getEmail();

        emailService.sendSimpleMessage(emailSeller, "THÔNG BÁO BẤT ĐỘNG SẢN " + nameRealEstate + " ĐÃ ĐƯỢC CHẤP THUẬN", "ADMIN thông báo bất động sản của bạn đăng đã được phê duyệt. Xin cảm ơn");

        realEstateRepository.save(realEstate);
    }

    public List<RealEstateResponse> getRealEstateByLocation(String location) {
        List<RealEstate> realEstates = realEstateRepository.findByLocation(location);
        return realEstates.stream()
                .map(realEstateMapper::toResponse)
                .collect(Collectors.toList());
    }


}
