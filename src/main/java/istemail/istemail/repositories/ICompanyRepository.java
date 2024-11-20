package istemail.istemail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import istemail.istemail.models.Company;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICompanyRepository extends JpaRepository<Company, UUID> {
    
    // FIND COMPANY BY NAME
    Company findByName(String name);

    // FIND COMPANY BY COUNTRY
    List<Company> findByCountry(String country);

    // FIND COMPANY BY ID
    Optional<Company> findCompanyById(UUID id);

    // FIND ALL COMPANIES
    List<Company> findAll();
}
