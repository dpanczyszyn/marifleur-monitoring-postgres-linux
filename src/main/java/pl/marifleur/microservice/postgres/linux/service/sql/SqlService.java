package pl.marifleur.microservice.postgres.linux.service.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.marifleur.microservice.postgres.linux.dto.error.ErrorDTO;
import pl.marifleur.microservice.postgres.linux.dto.sql.SqlDTO;
import pl.marifleur.microservice.postgres.linux.manager.sql.SqlExecutor;

import java.util.Arrays;
import java.util.Map;

@Service
public class SqlService {

    private final SqlExecutor sqlExecutor;

    @Autowired
    public SqlService(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    public ResponseEntity<Object> executeSQL(SqlDTO sqlDTO) {
        try {
            String outputString = sqlExecutor.executeSQL(sqlDTO);
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(Map.of(
                    "output", outputString
            ), HttpStatusCode.valueOf(200));
            return responseEntity;
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorDTO(
                            "error executing sql: " + sqlDTO + ", caused by: " + e.getMessage(),
                            Arrays.toString(e.getStackTrace())
                    ),
                    HttpStatusCode.valueOf(400)
            );
        }
    }
}
