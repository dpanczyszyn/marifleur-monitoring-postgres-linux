package pl.marifleur.microservice.postgres.linux.controller.dbms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marifleur.microservice.postgres.linux.service.dbms.DBMSService;

@RestController
@RequestMapping("/v1/dbms")
public class DBMSController {

    private final DBMSService dbmsService;

    @Autowired
    public DBMSController(DBMSService dbmsService) {
        this.dbmsService = dbmsService;
    }

    @GetMapping("/info")
    public ResponseEntity<Object> get() {
        return dbmsService.get();
    }
}
