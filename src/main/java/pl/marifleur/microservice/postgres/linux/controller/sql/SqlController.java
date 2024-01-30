package pl.marifleur.microservice.postgres.linux.controller.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marifleur.microservice.postgres.linux.dto.sql.SqlDTO;
import pl.marifleur.microservice.postgres.linux.service.sql.SqlService;

@RestController
@RequestMapping("/v1/sql")
public class SqlController {

    private final SqlService sqlService;

    @Autowired
    public SqlController(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    @PostMapping
    public ResponseEntity<Object> executeSQL(
            @RequestBody SqlDTO sqlDTO
    ) {
        return sqlService.executeSQL(sqlDTO);
    }
}
