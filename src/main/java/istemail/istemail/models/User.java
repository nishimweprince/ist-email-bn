package istemail.istemail.models;

import java.util.List;

import istemail.istemail.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User extends AbstractDomain {

    // FIRST NAME
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // LAST NAME
    @Column(name = "last_name", nullable = true)
    private String lastName;

    // EMAIL
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // PASSWORD
    @Column(name = "password", nullable = false)
    private String password;

    // PHONE NUMBER
    @Column(name = "phone_number", nullable = true, unique = true)
    private String phoneNumber;

    // IS VERIFIED
    @Column(name = "is_verified", nullable = false, columnDefinition = "boolean default false")
    private boolean isVerified;

    // TITLE
    @Column(name = "title", nullable = true)
    private String title;

    // ROLE
    @Column(name = "role", nullable = false, columnDefinition = "varchar(255) default 'STAFF'")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // SIGNATURES
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Signature> signatures;

    // COMPANY
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;
}
