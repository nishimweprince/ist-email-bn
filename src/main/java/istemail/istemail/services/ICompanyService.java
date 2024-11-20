package istemail.istemail.services;

import java.util.UUID;

import istemail.istemail.models.Company;
import istemail.istemail.payload.request.CreateCompanyDto;

public interface ICompanyService {
    String NAME = "ICompanyService";

    // CREATE COMPANY
    Company create(CreateCompanyDto createCompanyDto);

    // GET COMPANY BY ID
    Company getById(UUID id);

    // UPDATE COMPANY
    Company update(UUID id, CreateCompanyDto createCompanyDto);
}
