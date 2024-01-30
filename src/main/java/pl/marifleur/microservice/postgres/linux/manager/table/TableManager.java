package pl.marifleur.microservice.postgres.linux.manager.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marifleur.microservice.postgres.linux.configuration.DBMSConfiguration;
import pl.marifleur.microservice.postgres.linux.dto.column.ColumnDTO;
import pl.marifleur.microservice.postgres.linux.dto.table.TableDTO;
import pl.marifleur.microservice.postgres.linux.manager.sql.SqlExecutor;
import pl.marifleur.microservice.postgres.linux.util.process.ProcessUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class TableManager {

    private final SqlExecutor sqlExecutor;

    @Autowired
    public TableManager(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    public List<TableDTO> getTablesForDatabase(String databaseName) {
        String tablesAsString = sqlExecutor.executeSQL(
                "\\d",
                databaseName);

        List<String> tableStringList = new ArrayList<>(List.of(tablesAsString.split("\n")));
        tableStringList.removeIf(s -> s.trim().isEmpty());
        tableStringList.replaceAll(String::trim);

        List<TableDTO> tableDTOList = new ArrayList<>();

        for (String tableAsString : tableStringList) {
            TableDTO tableDTO = new TableDTO();
            String[] split = tableAsString.split("\\|");
            tableDTO.setSchema(split[0].trim());
            tableDTO.setName(split[1].trim());
            tableDTO.setType(split[2].trim());
            tableDTO.setOwner(split[3].trim());
            tableDTO.setColumns(getColumnsForTable(databaseName, tableDTO.getName()));
            tableDTOList.add(tableDTO);
        }

        return tableDTOList;
    }

    public List<ColumnDTO> getColumnsForTable(String databaseName, String tableName) {
        String columnsAsString = sqlExecutor.executeSQL(
                "\\d " + tableName,
                databaseName);

        List<String> columnStringList = new ArrayList<>(List.of(columnsAsString.split("\n")));
        columnStringList.removeIf(s -> s.trim().isEmpty());
        columnStringList.replaceAll(String::trim);

        List<ColumnDTO> columnDTOList = new ArrayList<>();

        for (String tableAsString : columnStringList) {
            ColumnDTO tableDTO = new ColumnDTO();
            String[] split = tableAsString.split("\\|");
            tableDTO.setName(split[0].trim());
            tableDTO.setType(split[1].trim());
            tableDTO.setCollation(split[2].trim());
            tableDTO.setNullable(split[3].trim());
            if (split.length == 5) {
                tableDTO.setDefaultValue(split[4].trim());
            } else {
                tableDTO.setDefaultValue("");
            }
            columnDTOList.add(tableDTO);
        }

        return columnDTOList;
    }
}
