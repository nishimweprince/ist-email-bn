package istemail.istemail.payload.response;

import istemail.istemail.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserRegistrationResponseDTO {
    private User user;
    private String token;
}
