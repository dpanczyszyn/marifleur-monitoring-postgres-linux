package pl.marifleur.microservice.postgres.linux.service.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.marifleur.microservice.postgres.linux.manager.database.DatabaseManager;

import java.util.List;

@Service
public class DatabaseService {

    private final DatabaseManager databaseManager;

    @Autowired
    public DatabaseService(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public ResponseEntity<Object> getDatabases() {
        List<String> databases = databaseManager.getAllDatabases();
        return new ResponseEntity<>(
                databases,
                HttpStatusCode.valueOf(200)
        );
    }
}
