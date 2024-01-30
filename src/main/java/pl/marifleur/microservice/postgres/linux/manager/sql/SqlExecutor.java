package pl.marifleur.microservice.postgres.linux.manager.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marifleur.lib.util.process.ProcessUtil;
import pl.marifleur.microservice.postgres.linux.configuration.DBMSConfiguration;
import pl.marifleur.microservice.postgres.linux.dto.sql.SqlDTO;

import java.util.Objects;

@Component
public class SqlExecutor {

    private final ProcessUtil processUtil = new ProcessUtil();
    private final DBMSConfiguration dbmsConfiguration;

    @Autowired
    public SqlExecutor(DBMSConfiguration dbmsConfiguration) {
        this.dbmsConfiguration = dbmsConfiguration;
    }

    public String executeSQL(SqlDTO sqlDTO) {

        String sqlCommand;

        if (Objects.isNull(sqlDTO.getDatabaseName()) || sqlDTO.getDatabaseName().trim().isEmpty()) {
            sqlCommand = "psql -t -c '" + sqlDTO.getSql() + "'";
        } else {
            sqlCommand = "psql -t -d " + sqlDTO.getDatabaseName() + " -c '" + sqlDTO.getSql() + "'";
        }

        try {
            return processUtil.runAsUser(
                    dbmsConfiguration.getUsername(),
                    dbmsConfiguration.getPassword(),
                    sqlCommand);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String executeSQL(String sql) {
        return executeSQL(new SqlDTO(
                sql,
                null
        ));
    }

    public String executeSQL(String sql, String databaseName) {
        return executeSQL(new SqlDTO(
                sql,
                databaseName
        ));
    }
}
