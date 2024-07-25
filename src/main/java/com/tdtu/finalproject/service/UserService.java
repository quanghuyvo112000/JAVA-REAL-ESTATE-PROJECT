package com.tdtu.finalproject.service;

import com.tdtu.finalproject.dto.request.user.*;
import com.tdtu.finalproject.dto.response.user.UserResponse;
import com.tdtu.finalproject.entity.Role;
import com.tdtu.finalproject.entity.User;
import com.tdtu.finalproject.exception.AppException;
import com.tdtu.finalproject.exception.ErrorCode;
import com.tdtu.finalproject.mapper.UserMapper;
import com.tdtu.finalproject.repository.RoleRepository;
import com.tdtu.finalproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;

    @Autowired
    private final EmailService emailService;

    @PreAuthorize("permitAll()")
    public UserResponse createUser(UserCreationRequest request) {

        /* Nếu username tồn tại thì hiện lỗi */
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        /* Nếu email tồn tại thì hiện lỗi */
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        User user = userMapper.toUser(request);

        /* Mã hóa password */
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Lấy danh sách vai trò từ yêu cầu
        var roles = request.getRole();

        // Nếu không có vai trò nào từ yêu cầu, gán mặc định là "CUSTOMER"
        if (roles == null || roles.isEmpty()) {
            Role defaultRole = roleRepository.findById("CUSTOMER")
                    .orElseThrow(() -> new AppException(ErrorCode.DEFAULT_ROLE_NOT_FOUND));
            user.setRole(Collections.singleton(defaultRole));
        } else {
            // Nếu có vai trò từ yêu cầu, gán chúng cho người dùng
            var existingRoles = roleRepository.findAllById(roles);
            user.setRole(new HashSet<>(existingRoles));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void resetPassword(UserResetPWRequest request) {
        // Kiểm tra xem email có tồn tại trong cơ sở dữ liệu không
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

        // Tạo mật khẩu ngẫu nhiên
        String newPassword = generateRandomPassword();

        // Mã hóa mật khẩu mới
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Lưu mật khẩu mới vào cơ sở dữ liệu
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Trả về đối tượng UserResponse cho việc reset mật khẩu thành công

        emailService.sendSimpleMessage(request.getEmail(), "RESET PASSWORD", "New password: "+ newPassword + "\n\n" +
                "Please change your password as soon as you log in successfully");

        userMapper.toUserResponse(user);
    }

    // Method to generate random password
    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(12);
    }

    public void changePassword(UserChangePWRequest request) {
        // Kiểm tra xem idUser có hợp lệ không
        User user = userRepository.findById(request.getIdUser())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Kiểm tra mật khẩu cũ có khớp với mật khẩu hiện tại không
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        // Mật khẩu cũ khớp, mã hóa mật khẩu mới và cập nhật mật khẩu trong cơ sở dữ liệu
        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }


    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        /*PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));*/


       return userMapper.toUserResponse(userRepository.save(user)) ;
    }

    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasAuthority('CREATE_DATA')")
    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        return userMapper.toListUserResponse(userRepository.findAll());
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException((ErrorCode.USER_NOT_EXISTED)));

        return userMapper.toUserResponse(user);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    public void updateUserRole(String userId, UserUpdateRoleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Kiểm tra xem có vai trò mới được cung cấp không
        if (request.getRole() == null || request.getRole().isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        // Lấy vai trò mới từ request và kiểm tra xem chúng có tồn tại không
        Set<Role> newRoles = new HashSet<>();
        for (String roleName : request.getRole()) {
            Role role = roleRepository.findById(roleName)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            newRoles.add(role);
        }

        // Cập nhật vai trò của người dùng
        user.setRole(newRoles);
        userRepository.save(user);

    }

}
