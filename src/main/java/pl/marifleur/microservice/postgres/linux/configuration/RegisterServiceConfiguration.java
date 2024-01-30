package pl.marifleur.microservice.postgres.linux.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import pl.marifleur.microservice.postgres.linux.util.request.APIRequestUtil;

@Configuration
public class RegisterServiceConfiguration {

    private final APIRequestUtil apiRequestUtil = new APIRequestUtil();
    private final Environment environment;
    private final String serverUrl;
    private final String applicationName;

    @Autowired
    public RegisterServiceConfiguration(Environment environment) {
        this.environment = environment;
        this.serverUrl = this.environment.getProperty("marifleur.monitoring-tool.server");
        this.applicationName = this.environment.getProperty("spring.application.name");
        registerService();
    }

    public void registerService() {
        System.out.println("serverUrl: " + serverUrl);
        System.out.println("applicationName: " + applicationName);
    }
}
