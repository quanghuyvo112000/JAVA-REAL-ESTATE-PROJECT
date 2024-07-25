package com.tdtu.finalproject.service;

import com.tdtu.finalproject.dto.request.transaction.TransactionRequest;
import com.tdtu.finalproject.dto.response.trasaction.TransactionResponse;
import com.tdtu.finalproject.entity.RealEstate;
import com.tdtu.finalproject.entity.Transaction;
import com.tdtu.finalproject.entity.User;
import com.tdtu.finalproject.mapper.TransactionMapper;
import com.tdtu.finalproject.repository.RealEstateRepository;
import com.tdtu.finalproject.repository.TransactionRepository;
import com.tdtu.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TransactionService {
    private final UserRepository userRepository;
    private final RealEstateRepository realEstateRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    private final EmailService emailService;
    @PreAuthorize("hasRole('BROKER')")
    public TransactionResponse createTransaction(TransactionRequest request){
        User user = userRepository.findById(request.getSellerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RealEstate realEstate = realEstateRepository.findById(request.getRealEstateId())
                .orElseThrow(() -> new RuntimeException("Real estate not found"));

        String LastNameSeller = user.getLastName();
        String FirstNameSeller = user.getFirstName();

        String fullnameSeller = LastNameSeller + " " + FirstNameSeller;

        String nameRealEstate = realEstate.getTitle().toUpperCase();

        double commissionRate;
        double amount = realEstate.getPrice();
        if (amount < 1000000000) {
            commissionRate = 0.05;
        } else if (amount <= 2000000000) {
            commissionRate = 0.08;
        } else if (amount > 2000000000){
            commissionRate = 0.1;
        } else {
            commissionRate = 0.0;
        }

        double commissionReceived = amount * commissionRate;

        String emailAdmin = "huyquangvo11.2000@gmail.com";

        emailService.sendSimpleMessage(emailAdmin, "THÔNG BÁO SẼ NHẬN TIỀN HOA HỒNG TỪ DỰ ÁN " + nameRealEstate, "Tin vui! Nhờ nỗ lực không ngừng nghỉ của nhà Môi Giới " + fullnameSeller + ", dự án " +
                nameRealEstate +" đã được bán thành công, đánh dấu một cột mốc quan trọng trong quá trình phát triển dự án. \n" +
                "Nhờ vậy, ADMIN sẽ nhận được hoa hồng hậu hĩnh là " + commissionReceived +" đồng. Xin gửi lời chúc mừng và tri ân đến đội ngũ môi giới đã không ngừng cống hiến. " +
                "Thành công này là động lực to lớn để chúng ta tiếp tục nỗ lực gặt hái nhiều thành tích hơn nữa trong tương lai.");



        Transaction transaction = transactionMapper.fromRequest(request);
        transaction.setAmount(amount);
        transaction.setCommission(commissionReceived);
        transaction.setSeller(user);
        transaction.setSellerName(fullnameSeller);
        transaction.setRealEstateName(nameRealEstate);
        transaction.setRealEstate(realEstate);
        Transaction saveTransaction = transactionRepository.save(transaction);

        activateRealEstates(request.getRealEstateId());

        return transactionMapper.toResponse(saveTransaction);
    }

    @PreAuthorize("hasRole('BROKER')")
    public void activateRealEstates(Long id) {
        RealEstate realEstate = realEstateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Real Estate not found"));
        realEstate.setStatus(false);

        String nameRealEstate = realEstate.getTitle().toUpperCase();

        String idSeller = realEstate.getUser().getId();

        User user = userRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        String emailSeller = user.getEmail();

        emailService.sendSimpleMessage(emailSeller, "THÔNG BÁO BẤT ĐỘNG SẢN " + nameRealEstate, "ADMIN thông báo bất động sản của bạn đăng sẽ về trạng thái chờ phê duyệt và chờ ADMIN xóa, vì đã thực hiện giao dịch."+"\n\n Xin cảm ơn");

        realEstateRepository.save(realEstate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<TransactionResponse> getAllTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('BROKER')")
    public List<TransactionResponse> getAllTransactionById(String userId){
        List<Transaction> transactions = transactionRepository.findBySellerId(userId);

        return transactions.stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());

    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deteleTransaction(Long id){
        transactionRepository.deleteById(id);
    }


}
