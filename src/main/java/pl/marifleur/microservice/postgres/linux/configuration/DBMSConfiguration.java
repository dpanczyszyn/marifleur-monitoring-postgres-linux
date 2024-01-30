package pl.marifleur.microservice.postgres.linux.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import pl.marifleur.lib.util.file.FileUtil;
import pl.marifleur.lib.util.net.NetUtil;
import pl.marifleur.microservice.postgres.linux.manager.sql.SqlExecutor;

@Configuration
public class DBMSConfiguration {

    private final FileUtil fileUtil = new FileUtil();
    private final NetUtil netUtil = new NetUtil();
    private final Environment environment;
    private final SqlExecutor sqlExecutor = new SqlExecutor(this);
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private String dataDirectory;
    @Getter
    private String logDirectory;
    @Getter
    private String configFile;
    @Getter
    private String logFileName;
    @Getter
    private String currentLogFilesPath;
    @Getter
    private String logFileExtension;
    @Getter
    private final String protocol = "jdbc:postgresql://";
    @Getter
    private String hostIpAddress;
    @Getter
    private int port;

    @Autowired
    public DBMSConfiguration(Environment environment) {
        this.environment = environment;
        username = this.environment.getProperty("marifleur.monitoring-tool.dbms.username");
        password = this.environment.getProperty("marifleur.monitoring-tool.dbms.password");
        dataDirectory = getDataDirectoryValue();
        logDirectory = getLogDirectoryValue();
        configFile = getConfigFileValue();
        logFileName = getLogFileNameValue();
        currentLogFilesPath = getCurrentLogFilesValue(fileUtil);
        logFileExtension = getLogFileExtensionValue(logFileName);
        hostIpAddress = getHostIpAddressValue();
        port = getPortValue();
    }

    private String getDataDirectoryValue() {
        return sqlExecutor.executeSQL("SHOW data_directory;").trim();
    }

    private String getLogDirectoryValue() {
        String output = sqlExecutor.executeSQL("SHOW log_directory;");
        return fileUtil.buildPath(dataDirectory, output.trim());
    }

    private String getConfigFileValue() {
        return sqlExecutor.executeSQL("SHOW config_file;").trim();
    }

    private String getLogFileNameValue() {
        return sqlExecutor.executeSQL("SHOW log_filename;").trim();
    }

    private String getLogFileExtensionValue(String logFileName) {
        int index = logFileName.lastIndexOf(".");
        return logFileName.substring(index);
    }

    private String getCurrentLogFilesValue(FileUtil fileUtil) {
        return fileUtil.buildPath(dataDirectory, "current_logfiles");
    }

    private String getHostIpAddressValue() {
        String listenAddress = sqlExecutor.executeSQL("SHOW listen_addresses;").trim();
        if (listenAddress.equals("localhost")) {
            try {
                return netUtil.getLocalHostIpAddress();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return listenAddress;
        }
    }

    private int getPortValue() {
        String output = sqlExecutor.executeSQL("SHOW port;").trim();
        return Integer.parseInt(output);
    }
}
