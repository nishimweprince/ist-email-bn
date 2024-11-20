package istemail.istemail.payload.response;

import istemail.istemail.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserLoginResponseDTO {
    private User user;
    private String token;
}
