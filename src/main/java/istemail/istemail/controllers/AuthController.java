package istemail.istemail.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import istemail.istemail.payload.request.UserRegistrationRequestDTO;
import istemail.istemail.payload.response.ResponseDto;
import istemail.istemail.payload.request.UserLoginRequestDTO;
import istemail.istemail.services.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        return ResponseEntity.ok(
                ResponseDto.success("User registered successfully", authService.register(userRegistrationRequestDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        return ResponseEntity.ok(ResponseDto.success("User logged in successfully",
                authService.login(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword())));
    }
}
