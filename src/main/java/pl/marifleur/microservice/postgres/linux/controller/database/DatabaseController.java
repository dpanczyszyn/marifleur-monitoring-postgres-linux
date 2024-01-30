package pl.marifleur.microservice.postgres.linux.controller.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marifleur.microservice.postgres.linux.service.database.DatabaseService;

@RestController
@RequestMapping("/v1/database")
public class DatabaseController {

    private final DatabaseService databaseService;

    @Autowired
    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping
    public ResponseEntity<Object> getDatabases() {
        return databaseService.getDatabases();
    }
}
