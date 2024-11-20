package istemail.istemail.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String title;
}
