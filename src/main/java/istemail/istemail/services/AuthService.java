package istemail.istemail.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import istemail.istemail.enums.UserRole;
import istemail.istemail.exceptions.ResourceAlreadyExistsException;
import istemail.istemail.exceptions.ResourceNotFoundException;
import istemail.istemail.models.User;
import istemail.istemail.payload.request.UserRegistrationRequestDTO;
import istemail.istemail.payload.response.UserLoginResponseDTO;
import istemail.istemail.payload.response.UserRegistrationResponseDTO;
import istemail.istemail.repositories.IUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;
    // REGISTER
    public UserRegistrationResponseDTO register(UserRegistrationRequestDTO userRegistrationRequestDTO) {
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
                .role(userRegistrationRequestDTO.getRole() != null ? userRegistrationRequestDTO.getRole() : UserRole.STAFF)
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return UserRegistrationResponseDTO.builder()
                .user(savedUser)
                .token(token)
                .build();
    }

    // LOGIN
    public UserLoginResponseDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email or password is incorrect"));
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Email or password is incorrect");
        }
        String token = jwtService.generateToken(user);
        return UserLoginResponseDTO.builder()
                .user(user)
                .token(token)
                .build();
    }
}
