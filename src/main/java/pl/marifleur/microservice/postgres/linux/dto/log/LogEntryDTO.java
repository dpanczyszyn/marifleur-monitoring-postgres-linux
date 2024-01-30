package pl.marifleur.microservice.postgres.linux.dto.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogEntryDTO {

    private String processId;
    private long dateInMillis;
    private String user;
    private String database;
    private String content;

    @Override
    public String toString() {
        return "LogEntryDTO{" +
                "processId='" + processId + '\'' +
                ", dateInMillis=" + dateInMillis +
                ", user='" + user + '\'' +
                ", database='" + database + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
