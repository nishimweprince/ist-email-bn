package istemail.istemail.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import istemail.istemail.payload.request.CreateCompanyDto;
import istemail.istemail.payload.response.ResponseDto;
import istemail.istemail.services.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody CreateCompanyDto createCompanyDto) {
        return ResponseEntity
                .ok(ResponseDto.success("Company created successfully", companyService.create(createCompanyDto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseDto.success("Company fetched successfully", companyService.getById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable UUID id, @RequestBody CreateCompanyDto createCompanyDto) {
        return ResponseEntity.ok(ResponseDto.success("Company updated successfully", companyService.update(id, createCompanyDto)));
    }
}
