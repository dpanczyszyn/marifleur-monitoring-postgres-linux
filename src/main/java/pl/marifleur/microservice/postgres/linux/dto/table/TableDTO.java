package pl.marifleur.microservice.postgres.linux.dto.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.marifleur.microservice.postgres.linux.dto.column.ColumnDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TableDTO {

    private String schema;
    private String name;
    private String type;
    private String owner;
    private List<ColumnDTO> columns;

    @Override
    public String toString() {
        return "TableDTO{" +
                "schema='" + schema + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", owner='" + owner + '\'' +
                ", columns=" + columns +
                '}';
    }
}
