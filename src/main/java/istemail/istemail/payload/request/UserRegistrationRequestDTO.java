package istemail.istemail.payload.request;

import istemail.istemail.enums.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class UserRegistrationRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole role;
}
