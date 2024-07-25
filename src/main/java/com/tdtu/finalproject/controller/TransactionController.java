package com.tdtu.finalproject.controller;

import com.tdtu.finalproject.dto.request.ApiResponse;
import com.tdtu.finalproject.dto.request.transaction.TransactionRequest;
import com.tdtu.finalproject.dto.response.trasaction.TransactionResponse;
import com.tdtu.finalproject.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/transaction")
public class TransactionController {
    final TransactionService transactionService;

    @PostMapping
    ApiResponse<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest request){
        ApiResponse<TransactionResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(transactionService.createTransaction(request));

        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<TransactionResponse>> getAllTransaction() {
        ApiResponse<List<TransactionResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(transactionService.getAllTransaction());
        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<List<TransactionResponse>> getAllTransactionById(@PathVariable String id) {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactionById(id);
        ApiResponse<List<TransactionResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(transactionResponses);
        return apiResponse;
    }
    @DeleteMapping("/{id}")
    ApiResponse<String> deteleTransaction(@PathVariable Long id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        transactionService.deteleTransaction(id);
        apiResponse.setCode(1000);
        apiResponse.setMessage("Transaction has been deleted");
        return apiResponse;
    }
}
