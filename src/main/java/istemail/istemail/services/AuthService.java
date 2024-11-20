package istemail.istemail.services;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import istemail.istemail.config.AppConfiguration;
import istemail.istemail.enums.UserRole;
import istemail.istemail.exceptions.ResourceAlreadyExistsException;
import istemail.istemail.exceptions.ResourceNotFoundException;
import istemail.istemail.models.User;
import istemail.istemail.payload.request.UserRegistrationRequestDTO;
import istemail.istemail.payload.response.UserLoginResponseDto;
import istemail.istemail.payload.response.UserRegistrationResponseDto;
import istemail.istemail.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;
    private final AppConfiguration appConfiguration;
    private final EmailService emailService;

    // REGISTER
    public String register(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        User userExists = userRepository.findByEmail(userRegistrationRequestDTO.getEmail()).orElse(null);
        if (userExists != null) {
            throw new ResourceAlreadyExistsException("User already exists");
        }
        User user = User.builder()
                .firstName(userRegistrationRequestDTO.getFirstName())
                .lastName(userRegistrationRequestDTO.getLastName())
                .email(userRegistrationRequestDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userRegistrationRequestDTO.getPassword()))
                .phoneNumber(userRegistrationRequestDTO.getPhoneNumber())
                .isVerified(false)
                .role(userRegistrationRequestDTO.getRole() != null ? userRegistrationRequestDTO.getRole()
                        : UserRole.STAFF)
                .build();

        // GENERATE VERIFICATION CODE
        String verificationCode = String.format("%06d", (int) (Math.random() * 1000000));
        user.setVerificationCode(verificationCode);

        User savedUser = userRepository.save(user);

        String emailContent = generateEmailContent(savedUser.getFirstName(), savedUser.getVerificationCode());
        // SEND EMAIL
        emailService.sendEmail(appConfiguration.getMailUsername(), savedUser.getEmail(), "Verify Your Email",
                emailContent);

        return "Email sent successfully";

    }

    // VERIFY EMAIL
    public UserRegistrationResponseDto verifyEmail(String email, String verificationCode) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getVerificationCode().equals(verificationCode)) {
            throw new ResourceNotFoundException("Verification code is incorrect");
        }
        user.isVerified();
        user.setVerificationCode(null);

        // GENERATE TOKEN
        String token = jwtService.generateToken(user);

        userRepository.save(user);
        return UserRegistrationResponseDto.builder()
                .user(user)
                .token(token)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
    }

    // LOGIN
    public UserLoginResponseDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email or password is incorrect"));
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Email or password is incorrect");
        }
        String token = jwtService.generateToken(user);
        return UserLoginResponseDto.builder()
                .user(user)
                .token(token)
                .build();
    }

    private String generateEmailContent(String firstName, String verificationCode) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>Email Verification</title>
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px;">
                  <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                    <div style="background-color: #174655; padding: 30px; text-align: center;">
                      <h1 style="margin: 0; color: #ffffff; font-size: 24px;">Email Verification</h1>
                    </div>
                    <div style="padding: 40px;">
                      <p style="margin: 0; color: #333; font-size: 16px;">Hello %s,</p>
                      <p style="margin-top: 20px; color: #555; font-size: 16px;">Thank you for creating an account. To complete your registration, please use the following verification code:</p>
                      <div style="margin: 30px 0; text-align: center;">
                        <div style="display: inline-block; background-color: #f8f8f8; border: 2px solid #174655; border-radius: 8px; padding: 20px 40px;">
                          <span style="font-size: 32px; font-weight: bold; color: #174655; letter-spacing: 8px;">%s</span>
                        </div>
                      </div>
                      <p style="margin: 0; color: #666; font-size: 14px;">This verification code will expire in 10 minutes.</p>
                      <p style="margin-top: 20px; color: #666; font-size: 14px;">If you didn't request this verification code, please ignore this email.</p>
                      <div style="margin-top: 40px; border-top: 1px solid #eee; padding-top: 20px;">
                        <p style="margin: 0; color: #333; font-size: 16px;">Best regards,<br>Our Team</p>
                      </div>
                    </div>
                    <div style="background-color: #174655; padding: 20px; text-align: center;">
                      <p style="margin: 0; color: #ffffff; font-size: 12px;">&copy; 2024 Our Service. All rights reserved.</p>
                    </div>
                  </div>
                </body>
                </html>
                """
                .formatted(firstName, verificationCode);
    }

}
