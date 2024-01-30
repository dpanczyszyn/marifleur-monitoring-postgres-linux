package pl.marifleur.microservice.postgres.linux.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import pl.marifleur.microservice.postgres.linux.dto.registration.RegistrationDTO;
import pl.marifleur.microservice.postgres.linux.util.net.NetUtil;
import pl.marifleur.microservice.postgres.linux.util.request.APIRequestUtil;

@Configuration
public class RegisterServiceConfiguration {

    private final APIRequestUtil apiRequestUtil = new APIRequestUtil();
    private final NetUtil netUtil = new NetUtil();
    private final ServerProperties serverProperties;
    private final Environment environment;
    private final String serverUrl;
    private final String applicationName;
    private final String baseUrl;

    @Autowired
    public RegisterServiceConfiguration(ServerProperties serverProperties, Environment environment) {
        this.serverProperties = serverProperties;
        this.environment = environment;

        this.serverUrl = this.environment.getProperty("marifleur.monitoring-tool.server");
        this.applicationName = this.environment.getProperty("spring.application.name");
        try {
            this.baseUrl = netUtil.getMicroserviceBaseUrl(this.serverProperties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        registerService();
    }

    public void registerService() {
        RegistrationDTO registrationDTO = new RegistrationDTO(
                applicationName,
                baseUrl,
                "PostgreSQL"
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<String> responseEntity = apiRequestUtil.sendAndReceiveResponse(
                    serverUrl + "/v1/dbms",
                    HttpMethod.POST,
                    httpHeaders,
                    registrationDTO
            );

            if (!responseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
                throw new RuntimeException("Return code != 200 at registration");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
