package com.tdtu.finalproject.controller;

import com.tdtu.finalproject.dto.request.ApiResponse;
import com.tdtu.finalproject.dto.request.user.UserCreationRequest;
import com.tdtu.finalproject.dto.request.user.UserUpdateRequest;
import com.tdtu.finalproject.dto.request.user.UserUpdateRoleRequest;
import com.tdtu.finalproject.dto.response.user.UserResponse;
import com.tdtu.finalproject.dto.response.user.UserUpdateRoleResponse;
import com.tdtu.finalproject.entity.User;
import com.tdtu.finalproject.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(userService.createUser(request));

        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(userService.getUsers());
        return apiResponse;
    }


    /* Chỉ lấy đúng thông tin user đăng nhập */
    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(userService.getMyInfo());
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(userService.updateUser(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable String id){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        userService.deleteUser(id);
        apiResponse.setCode(1000);
        apiResponse.setMessage("User has been delete");
        return apiResponse;
    }

    @PutMapping("/{id}/role")
    ApiResponse<UserUpdateRoleResponse> updateUserRole(@PathVariable String id, @RequestBody UserUpdateRoleRequest request) {
        ApiResponse<UserUpdateRoleResponse> apiResponse = new ApiResponse<>();

        // Cập nhật vai trò của người dùng
        userService.updateUserRole(id, request);

        // Trả về thông báo thành công
        apiResponse.setCode(1000);
        apiResponse.setMessage("User roles updated successfully");

        return apiResponse;
    }

}
