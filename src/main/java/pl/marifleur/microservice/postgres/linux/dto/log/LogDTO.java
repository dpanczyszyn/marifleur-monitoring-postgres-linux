package pl.marifleur.microservice.postgres.linux.dto.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogDTO {

    private String logFileName;
    private long creationDate;
    private String creationDateFormatted;
    private List<LogEntryDTO> content;
    private String logFilePath;
}
