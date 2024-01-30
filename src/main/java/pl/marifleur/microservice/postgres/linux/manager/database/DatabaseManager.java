package pl.marifleur.microservice.postgres.linux.manager.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marifleur.microservice.postgres.linux.configuration.DBMSConfiguration;
import pl.marifleur.microservice.postgres.linux.dto.sql.SqlDTO;
import pl.marifleur.microservice.postgres.linux.manager.sql.SqlExecutor;
import pl.marifleur.microservice.postgres.linux.manager.table.TableManager;
import pl.marifleur.microservice.postgres.linux.util.process.ProcessUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseManager {

    private final SqlExecutor sqlExecutor;

    @Autowired
    public DatabaseManager(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    public List<String> getAllDatabases() {
        String databasaesAsString = sqlExecutor.executeSQL("SELECT datname FROM pg_database");

        List<String> databasesList = new ArrayList<>(List.of(databasaesAsString.split("\n")));
        databasesList.removeIf(s -> s.trim().isEmpty());
        databasesList.replaceAll(String::trim);
        databasesList.removeIf(s -> s.equals("postgres"));
        databasesList.removeIf(s -> s.equals("template0"));
        databasesList.removeIf(s -> s.equals("template1"));
        return databasesList;
    }
}
