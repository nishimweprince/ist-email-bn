package istemail.istemail.services;

import java.util.UUID;

import istemail.istemail.models.User;
import istemail.istemail.payload.request.UpdateUserDto;

public interface IUserService {
    String NAME = "userService";

    // UPDATE USER
    User update(UUID id, UpdateUserDto updateUserDto);

    // GENERATE SIGNATURE
    String generateSignature(UUID id);

    // JOIN COMPANY
    User joinCompany(UUID id, UUID companyId);
}
