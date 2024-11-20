package istemail.istemail.payload.response;

import istemail.istemail.models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserLoginResponseDto {
    private User user;
    private String token;
}
