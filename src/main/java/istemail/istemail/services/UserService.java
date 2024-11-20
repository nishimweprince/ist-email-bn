package istemail.istemail.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import istemail.istemail.exceptions.ResourceNotFoundException;
import istemail.istemail.models.Company;
import istemail.istemail.models.User;
import istemail.istemail.payload.request.UpdateUserDto;
import istemail.istemail.repositories.ICompanyRepository;
import istemail.istemail.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;

@Service(IUserService.NAME)
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ICompanyRepository companyRepository;

    // UPDATE USER
    @Override
    public User update(UUID id, UpdateUserDto updateUserDto) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (updateUserDto.getFirstName() != null) {
            user.setFirstName(updateUserDto.getFirstName());
        }
        if (updateUserDto.getLastName() != null) {
            user.setLastName(updateUserDto.getLastName());
        }
        if (updateUserDto.getEmail() != null) {
            user.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserDto.getPhoneNumber());
        }
        if (updateUserDto.getTitle() != null) {
            user.setTitle(updateUserDto.getTitle());
        }
        return userRepository.save(user);
    }

    @Override
    public String generateSignature(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Company company = companyRepository.findById(user.getCompany().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        // Create the HTML for the signature
        String signature = "<table style=\"font-family: Arial, sans-serif; font-size: 14px; color: #333; line-height: 1.5;\">\n"
                + "<tr>\n"
                + "  <td><strong style=\"font-size: 16px;\">" + user.getFirstName() + " "
                + (user.getLastName() != null ? user.getLastName() : "") + "</strong></td>\n"
                + "</tr>\n"
                + (user.getTitle() != null
                        ? "<tr><td style=\"font-size: 14px;\">" + user.getTitle() + "</td></tr>\n"
                        : "")
                + (company.getPhoneNumber() != null
                        ? "<tr><td style=\"font-size: 14px;\"><span style=\"margin-right: 5px;\">📞</span>"
                                + "<a href=\"tel:" + company.getPhoneNumber()
                                + "\" style=\"color: #333; text-decoration: none;\">"
                                + company.getPhoneNumber() + "</a></td></tr>\n"
                        : "")
                + "<tr><td style=\"font-size: 14px; font-weight: bold;\">" + company.getName() + "</td></tr>\n"
                + (company.getCity() != null || company.getAddress() != null || company.getCountry() != null
                        ? "<tr><td style=\"font-size: 14px;\">"
                                + (company.getCity() != null ? company.getCity() : "")
                                + (company.getAddress() != null ? ", " + company.getAddress() : "")
                                + (company.getCountry() != null ? ", " + company.getCountry() : "")
                                + "</td></tr>\n"
                        : "")
                + (company.getWebsiteUrl() != null
                        ? "<tr><td style=\"font-size: 14px;\"><span style=\"margin-right: 5px;\">🌐</span>"
                                + "<a href=\"" + company.getWebsiteUrl()
                                + "\" style=\"color: #0066cc; text-decoration: none;\">"
                                + company.getWebsiteUrl().replace("http://", "").replace("https://", "")
                                + "</a></td></tr>\n"
                        : "")
                + (company.getMissionStatement() != null
                        ? "<tr><td style=\"font-size: 12px; font-style: italic; color: #666;\">\""
                                + company.getMissionStatement() + "\"</td></tr>\n"
                        : "")
                + "</table>";

        return signature;
    }

    // JOIN COMPANY
    @Override
    public User joinCompany(UUID id, UUID companyId) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        user.setCompany(company);
        return userRepository.save(user);
    }
}
