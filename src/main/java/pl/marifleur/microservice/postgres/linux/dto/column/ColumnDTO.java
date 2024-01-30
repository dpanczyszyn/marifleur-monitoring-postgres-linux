package pl.marifleur.microservice.postgres.linux.dto.column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ColumnDTO {

    private String name;
    private String type;
    private String collation;
    private String nullable;
    private String defaultValue;

    @Override
    public String toString() {
        return "ColumnDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", collation='" + collation + '\'' +
                ", nullable='" + nullable + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}
