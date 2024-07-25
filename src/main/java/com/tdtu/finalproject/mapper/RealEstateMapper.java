package com.tdtu.finalproject.mapper;

import com.tdtu.finalproject.dto.request.realEstate.RealEstateRequest;
import com.tdtu.finalproject.dto.request.realEstate.UpdateRealEstateRequest;
import com.tdtu.finalproject.dto.response.RealEstate.RealEstateResponse;
import com.tdtu.finalproject.entity.RealEstate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RealEstateMapper {

    RealEstateRequest toRequest(RealEstate realEstate);
    @Mapping(target = "status", ignore = true)
    RealEstate fromRequest(RealEstateRequest request);

    RealEstateResponse toResponse(RealEstate realEstate);
    @Mapping(target = "user.id", ignore = true)
    RealEstate updateRealEstate(@MappingTarget RealEstate existingRealEstate, UpdateRealEstateRequest request);
}
