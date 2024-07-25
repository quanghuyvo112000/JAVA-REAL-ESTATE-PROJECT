package com.tdtu.finalproject.mapper;

import com.tdtu.finalproject.dto.request.transaction.TransactionRequest;
import com.tdtu.finalproject.dto.response.trasaction.TransactionResponse;
import com.tdtu.finalproject.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface TransactionMapper {

    TransactionResponse toResponse (Transaction transaction);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commission", ignore = true)
    Transaction fromRequest(TransactionRequest request);
}
