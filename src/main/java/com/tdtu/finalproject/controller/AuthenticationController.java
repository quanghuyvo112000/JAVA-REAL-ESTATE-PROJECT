package com.tdtu.finalproject.controller;

import com.nimbusds.jose.JOSEException;
import com.tdtu.finalproject.dto.request.ApiResponse;
import com.tdtu.finalproject.dto.request.authentication.AuthenticationRequest;
import com.tdtu.finalproject.dto.request.authentication.IntrospectRequest;
import com.tdtu.finalproject.dto.request.user.UserChangePWRequest;
import com.tdtu.finalproject.dto.request.user.UserResetPWRequest;
import com.tdtu.finalproject.dto.response.authentication.AuthenticationResponse;
import com.tdtu.finalproject.dto.response.authentication.IntrospectResponse;
import com.tdtu.finalproject.exception.AppException;
import com.tdtu.finalproject.service.AuthenticationService;
import com.tdtu.finalproject.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/authen")
public class AuthenticationController {

    AuthenticationService authenticationService;
    UserService userService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> autheticate(@RequestBody AuthenticationRequest request){
      var result =  authenticationService.autheticate(request);

      return ApiResponse.<AuthenticationResponse>builder()
              .result(result)
              .build();
    }


    @PostMapping("/reset-password")
    public ApiResponse<Object> resetPassword(@RequestBody UserResetPWRequest request) {
        try {
            userService.resetPassword(request);
            return ApiResponse.builder()
                    .code(1000)
                    .message("Reset password successfully")
                    .build();
        } catch (AppException e) {
            return ApiResponse.builder()
                    .code(e.getErrorCode().getCode()) // Assuming ErrorCode has getCode() method
                    .message(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/change-password")
    public ApiResponse<Object> changePassword(@RequestBody UserChangePWRequest request) {
        try {
            userService.changePassword(request);
            return ApiResponse.builder()
                    .code(1000)
                    .message("Change password successfully")
                    .build();
        } catch (AppException e) {
            return ApiResponse.builder()
                    .code(e.getErrorCode().getCode()) // Assuming ErrorCode has getCode() method
                    .message(e.getMessage())
                    .build();
        }
    }

    /* Kiểm tra token còn hiệu lực không hoặc token có bị thay đổi không */
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result =  authenticationService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
