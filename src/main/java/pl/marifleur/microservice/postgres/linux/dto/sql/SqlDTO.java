package pl.marifleur.microservice.postgres.linux.dto.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SqlDTO {

    private String sql;
    private String databaseName;

    @Override
    public String toString() {
        return "SqlDTO{" +
                "sql='" + sql + '\'' +
                '}';
    }
}
