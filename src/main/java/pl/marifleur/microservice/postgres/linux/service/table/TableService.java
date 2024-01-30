package pl.marifleur.microservice.postgres.linux.service.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.marifleur.microservice.postgres.linux.dto.error.ErrorDTO;
import pl.marifleur.microservice.postgres.linux.dto.table.TableDTO;
import pl.marifleur.microservice.postgres.linux.manager.table.TableManager;

import java.util.List;
import java.util.Objects;

@Service
public class TableService {

    private final TableManager tableManager;

    @Autowired
    public TableService(TableManager tableManager) {
        this.tableManager = tableManager;
    }

    public ResponseEntity<Object> getTables(String databaseName) {
        if (Objects.isNull(databaseName) || databaseName.trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ErrorDTO(
                            "request 'name' parameter cannot be null or empty",
                            null
                    ),
                    HttpStatusCode.valueOf(400)
            );
        }

        List<TableDTO> tablesForDatabase = tableManager.getTablesForDatabase(databaseName);

        return new ResponseEntity<>(
                tablesForDatabase,
                HttpStatusCode.valueOf(200)
        );
    }
}
