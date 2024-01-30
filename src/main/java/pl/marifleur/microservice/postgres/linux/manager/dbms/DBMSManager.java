package pl.marifleur.microservice.postgres.linux.manager.dbms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marifleur.lib.util.cpu.CPUInfo;
import pl.marifleur.lib.util.cpu.CPUInfoUtil;
import pl.marifleur.lib.util.disk.DiskUsageUtil;
import pl.marifleur.microservice.postgres.linux.configuration.DBMSConfiguration;
import pl.marifleur.microservice.postgres.linux.dto.dbms.DbmsDTO;

@Component
public class DBMSManager {

    private final DiskUsageUtil diskUsageUtil = new DiskUsageUtil();
    private final CPUInfoUtil cpuInfoUtil = new CPUInfoUtil();
    private final DBMSConfiguration dbmsConfiguration;

    @Autowired
    public DBMSManager(DBMSConfiguration dbmsConfiguration) {
        this.dbmsConfiguration = dbmsConfiguration;
    }

    public DbmsDTO getDbmsDTO() {
        DbmsDTO dbmsDTO = new DbmsDTO();
        dbmsDTO.setUrl(dbmsConfiguration.getProtocol() + dbmsConfiguration.getHostIpAddress() + ":" + dbmsConfiguration.getPort());
        dbmsDTO.setDataDirectory(dbmsConfiguration.getDataDirectory());
        dbmsDTO.setLogDirectory(dbmsConfiguration.getLogDirectory());
        dbmsDTO.setConfigFile(dbmsConfiguration.getConfigFile());
        dbmsDTO.setLogFileName(dbmsConfiguration.getLogFileName());
        dbmsDTO.setDiskUsage(diskUsageUtil.getCurrentPartitionDiskUsage());
        dbmsDTO.setCpuInfo(new CPUInfo(
                cpuInfoUtil.getCPUUsage(),
                cpuInfoUtil.getNumberOfCPUs()
        ));

        return dbmsDTO;
    }
}
