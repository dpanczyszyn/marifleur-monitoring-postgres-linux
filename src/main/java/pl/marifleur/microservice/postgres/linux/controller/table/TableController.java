package pl.marifleur.microservice.postgres.linux.controller.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.marifleur.microservice.postgres.linux.service.table.TableService;

@RestController
@RequestMapping("/v1/table")
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public ResponseEntity<Object> getTables(
            @RequestParam(value = "database-name", required = false) String databaseName
    ) {
        return tableService.getTables(databaseName);
    }
}
