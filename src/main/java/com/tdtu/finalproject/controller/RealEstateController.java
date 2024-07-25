package com.tdtu.finalproject.controller;

import com.tdtu.finalproject.dto.request.ApiResponse;
import com.tdtu.finalproject.dto.request.realEstate.RealEstateRequest;
import com.tdtu.finalproject.dto.request.realEstate.UpdateRealEstateRequest;
import com.tdtu.finalproject.dto.response.RealEstate.RealEstateResponse;
import com.tdtu.finalproject.service.RealEstateService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/real-estate")
public class RealEstateController {

    final RealEstateService realEstateService;

    @PostMapping
    ApiResponse<RealEstateResponse> createRealEstate(@RequestBody @Valid RealEstateRequest request) {
        ApiResponse<RealEstateResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(realEstateService.addRealEstate(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<RealEstateResponse>> getAllRealEstates() {
        ApiResponse<List<RealEstateResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(realEstateService.getAllRealEstates());
        return apiResponse;
    }

    @GetMapping("/location/{location}")
    ApiResponse<List<RealEstateResponse>> getRealEstateByLocation(@PathVariable String location) {
        List<RealEstateResponse> realEstateResponses = realEstateService.getRealEstateByLocation(location);
        ApiResponse<List<RealEstateResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(realEstateResponses);
        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<RealEstateResponse> getRealEstateById(@PathVariable Long id) {
        ApiResponse<RealEstateResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(realEstateService.getRealEstateById(id));
        return apiResponse;
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<RealEstateResponse>> getRealEstateByUserId(@PathVariable String userId) {
        List<RealEstateResponse> realEstateResponses = realEstateService.getRealEstateByUserId(userId);
        ApiResponse<List<RealEstateResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(realEstateResponses);
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<RealEstateResponse> updateRealEstate(@PathVariable Long id, @RequestBody @Valid UpdateRealEstateRequest request) {
        ApiResponse<RealEstateResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(realEstateService.updateRealEstate(id, request));
        return apiResponse;
    }

    @PutMapping("/{id}/activate")
    public ApiResponse<Object> activateRealEstate(@PathVariable Long id) {
        realEstateService.activateRealEstate(id);
        return ApiResponse.builder()
                .code(1000)
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteRealEstate(@PathVariable Long id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        realEstateService.deleteRealEstate(id);
        apiResponse.setCode(1000);
        apiResponse.setMessage("Real estate has been deleted");
        return apiResponse;
    }

}
