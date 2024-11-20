package istemail.istemail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class AppConfiguration {

    @Value("${jwt.expiration}")
    private Long jwtExpiration;
}
