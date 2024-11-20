package istemail.istemail.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyDto {
    private String name;
    private String country;
    private String city;
    private String address;
    private String missionStatement;
    private String websiteUrl;
    private String phoneNumber;
}
