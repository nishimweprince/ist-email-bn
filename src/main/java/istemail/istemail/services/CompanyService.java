package istemail.istemail.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import istemail.istemail.exceptions.ResourceNotFoundException;
import istemail.istemail.models.Company;
import istemail.istemail.payload.request.CreateCompanyDto;
import istemail.istemail.repositories.ICompanyRepository;
import lombok.RequiredArgsConstructor;

@Service(ICompanyService.NAME)
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {
    private final ICompanyRepository companyRepository;

    // CREATE COMPANY
    @Override
    public Company create(CreateCompanyDto createCompanyDto) {
        Company company = Company.builder()
            .name(createCompanyDto.getName())
            .country(createCompanyDto.getCountry())
            .city(createCompanyDto.getCity())
            .address(createCompanyDto.getAddress())
            .missionStatement(createCompanyDto.getMissionStatement())
            .websiteUrl(createCompanyDto.getWebsiteUrl())
            .phoneNumber(createCompanyDto.getPhoneNumber())
            .build();
        return companyRepository.save(company);
    }

    // GET COMPANY BY ID
    @Override
    public Company getById(UUID id) {
        return companyRepository.findCompanyById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    // UPDATE COMPANY
    @Override
    public Company update(UUID id, CreateCompanyDto createCompanyDto) {
        Company company = getById(id);
        if (createCompanyDto.getName() != null) {
            company.setName(createCompanyDto.getName());
        }
        if (createCompanyDto.getCountry() != null) {
            company.setCountry(createCompanyDto.getCountry());
        }
        if (createCompanyDto.getCity() != null) {
            company.setCity(createCompanyDto.getCity());
        }
        if (createCompanyDto.getAddress() != null) {
            company.setAddress(createCompanyDto.getAddress());
        }
        if (createCompanyDto.getMissionStatement() != null) {
            company.setMissionStatement(createCompanyDto.getMissionStatement());
        }
        if (createCompanyDto.getWebsiteUrl() != null) {
            company.setWebsiteUrl(createCompanyDto.getWebsiteUrl());
        }
        if (createCompanyDto.getPhoneNumber() != null) {
            company.setPhoneNumber(createCompanyDto.getPhoneNumber());
        }
        return companyRepository.save(company);
    }
}
