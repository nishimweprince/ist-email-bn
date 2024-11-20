package istemail.istemail.controllers;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import istemail.istemail.payload.request.UpdateUserDto;
import istemail.istemail.payload.response.ResponseDto;
import istemail.istemail.services.IUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private final IUserService userService;

    // UPDATE USER
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable UUID id, @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity
                .ok(ResponseDto.success("User updated successfully", userService.update(id, updateUserDto)));
    }

    // GENERATE SIGNATURE
    @GetMapping("/{id}/signature")
    public ResponseEntity<ResponseDto> generateSignature(@PathVariable UUID id) {
        return ResponseEntity
                .ok(ResponseDto.success("Signature generated successfully", Map.of("signature", userService.generateSignature(id))));
    }

    // JOIN COMPANY
    @PatchMapping("/{id}/join-company")
    public ResponseEntity<ResponseDto> joinCompany(@PathVariable UUID id, @RequestBody Map<String, UUID> request) {
        return ResponseEntity
                .ok(ResponseDto.success("User joined company successfully",
                        userService.joinCompany(id, request.get("companyId"))));
    }
}
