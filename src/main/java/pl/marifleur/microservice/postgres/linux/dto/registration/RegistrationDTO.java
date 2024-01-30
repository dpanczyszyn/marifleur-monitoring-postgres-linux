package pl.marifleur.microservice.postgres.linux.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistrationDTO {

    private String applicationName;
    private String baseUrl;
    private String dbmsType;
}
