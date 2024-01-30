package pl.marifleur.microservice.postgres.linux.controller.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.marifleur.microservice.postgres.linux.service.log.LogFileService;

@RestController
@RequestMapping("/v1/logs")
public class LogFileController {

    LogFileService logFileService;

    @Autowired
    public LogFileController(LogFileService logFileService) {
        this.logFileService = logFileService;
    }


    @GetMapping
    public ResponseEntity<Object> get(
            @RequestParam(value = "type", required = false) String type
    ) {
        return logFileService.get(type);
    }
}
