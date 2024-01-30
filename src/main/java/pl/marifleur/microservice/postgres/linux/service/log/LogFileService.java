package pl.marifleur.microservice.postgres.linux.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.marifleur.microservice.postgres.linux.dto.error.ErrorDTO;
import pl.marifleur.microservice.postgres.linux.manager.log.LogFileReader;

import java.util.Arrays;
import java.util.Objects;

@Service
public class LogFileService {

    private final LogFileReader logFileReader;

    @Autowired
    public LogFileService(LogFileReader logFileReader) {
        this.logFileReader = logFileReader;
    }

    public ResponseEntity<Object> get(String type) {
        if (Objects.isNull(type) || type.trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ErrorDTO(
                            "request 'type' parameter cannot be null or empty",
                            null
                    ),
                    HttpStatusCode.valueOf(400)
            );
        }

        Object responseObject;

        try {
            if (type.equals("all")) {
                responseObject = logFileReader.readLogFiles();
            } else if (type.equals("recent")) {
                responseObject = logFileReader.readRecentLogFile();
            } else {
                return new ResponseEntity<>(
                        new ErrorDTO(
                                "request 'type' parameter's value must be 'all' or 'recent'",
                                null
                        ),
                        HttpStatusCode.valueOf(400));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorDTO(
                            "Error occurred during getting log files, caused by: " + e.getMessage(),
                            Arrays.toString(e.getStackTrace())
                    ),
                    HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<>(
                responseObject,
                HttpStatusCode.valueOf(200));
    }
}
