package istemail.istemail.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    // COUNTRY
    @Column(name = "country", nullable = true)
    private String country;

    // CITY
    @Column(name = "city", nullable = true)
    private String city;

    // WEBSITE URL
    @Column(name = "website_url", nullable = true)
    private String websiteUrl;

    // LOGO URL
    @Column(name = "logo_url", nullable = true)
    private String logoUrl;

    // PHONE NUMBER
    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    // USERS
    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<User> users;
}
