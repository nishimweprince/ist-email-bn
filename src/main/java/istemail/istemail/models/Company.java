package istemail.istemail.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "companies")
public class Company extends AbstractDomain {
    // NAME
    @Column(name = "name", nullable = false)
    private String name;

    // MISSION STATEMENT
    @Column(name = "mission_statement", nullable = true)
    private String missionStatement;

    // ADDRESS
    @Column(name = "address", nullable = true)
    private String address;

    // WEBSITE URL
    @Column(name = "website_url", nullable = true)
    private String websiteUrl;

    // LOGO URL
    @Column(name = "logo_url", nullable = true)
    private String logoUrl;

    // USERS
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
}
