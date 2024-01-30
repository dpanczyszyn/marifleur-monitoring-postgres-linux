package pl.marifleur.microservice.postgres.linux.dto.dbms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.marifleur.lib.util.cpu.CPUInfo;
import pl.marifleur.lib.util.disk.DiskUsage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DbmsDTO {

    private String url;
    private String dataDirectory;
    private String logDirectory;
    private String configFile;
    private String logFileName;
    private DiskUsage diskUsage;
    private CPUInfo cpuInfo;
}
