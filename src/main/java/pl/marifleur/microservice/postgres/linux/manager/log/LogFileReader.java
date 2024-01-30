package pl.marifleur.microservice.postgres.linux.manager.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marifleur.lib.util.file.FileUtil;
import pl.marifleur.lib.util.format.DateTimeFormatUtil;
import pl.marifleur.lib.util.process.ProcessUtil;
import pl.marifleur.microservice.postgres.linux.configuration.DBMSConfiguration;
import pl.marifleur.microservice.postgres.linux.dto.log.LogDTO;
import pl.marifleur.microservice.postgres.linux.dto.log.LogEntryDTO;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@Component
public class LogFileReader {

    private final ProcessUtil processUtil = new ProcessUtil();
    private final FileUtil fileUtil = new FileUtil();
    private final DateTimeFormatUtil dateTimeFormatUtil = new DateTimeFormatUtil();
    private final DBMSConfiguration dbmsConfiguration;

    @Autowired
    public LogFileReader(DBMSConfiguration dbmsConfiguration) {
        this.dbmsConfiguration = dbmsConfiguration;
    }

    public List<LogDTO> readLogFiles() throws Exception {
        List<LogDTO> logDTOList = new LinkedList<>();
        String logDirectory = dbmsConfiguration.getLogDirectory();
        File[] childFiles = fileUtil.getChildrenFiles(logDirectory);

        for (File childFile : childFiles) {
            LogDTO logDTO = readLogFile(childFile.getName());
            logDTOList.add(logDTO);
        }

        return logDTOList;
    }

    public LogDTO readLogFile(String logFileName) throws Exception {
        String username = dbmsConfiguration.getUsername();
        String password = dbmsConfiguration.getPassword();
        String logDirectory = dbmsConfiguration.getLogDirectory();
        File logFile = fileUtil.getFile(logDirectory, logFileName);

        if (logFile.isFile() && logFile.getName().endsWith(dbmsConfiguration.getLogFileExtension())) {
            String logOutput = processUtil.runAsUser(username, password, "cat " + logFile.getAbsolutePath());

            BasicFileAttributes baseFileAttributes = fileUtil.getBaseFileAttributes(logFile.getAbsolutePath());
            long creationDateInMillis = baseFileAttributes.creationTime().toMillis();
            Date creationDate = new Date(creationDateInMillis);

            String creationDateAsString = dateTimeFormatUtil.formatYYYYMMDDHHMMWithHyphens(creationDate);

            LogDTO logDTO = new LogDTO();
            logDTO.setLogFileName(logFile.getName());
            logDTO.setCreationDate(creationDateInMillis);
            logDTO.setCreationDateFormatted(creationDateAsString);
            logDTO.setContent(getLogEntries(logOutput));
            logDTO.setLogFilePath(logFile.getAbsolutePath());
            return logDTO;
        } else {
            throw new Exception("Error while reading log file");
        }
    }

    public LogDTO readRecentLogFile() throws Exception {
        return readLogFile(getCurrentLogFileName());
    }

    public List<LogEntryDTO> getLogEntries(String logContent) {
        List<LogEntryDTO> logEntryDTOList = new LinkedList<>();
        String[] array = logContent.split("\n");
        Collections.reverse(Arrays.asList(array));
        String contentPassed = "";

        for (String logString : array) {

            if (logString.startsWith(" ") || logString.startsWith("\t")) {
                contentPassed += logString + "\n";
            } else {
                String contentFromPrevious = contentPassed;
                contentPassed = "";
                String[] contentArray = contentFromPrevious.split("\n");
                Collections.reverse(Arrays.asList(contentArray));
                contentFromPrevious = String.join("\n", contentArray);

                int startIdIndex = logString.indexOf("[");
                int endIdIndex = logString.indexOf("]");
                String helperString;

                LogEntryDTO logEntryDTO = new LogEntryDTO();
                logEntryDTO.setDateInMillis(dateTimeFormatUtil.getDateFromPostgresLog(logString.substring(0, startIdIndex - 1)).getTime());
                logEntryDTO.setProcessId(logString.substring(startIdIndex + 1, endIdIndex));

                helperString = logString.substring(endIdIndex + 1).trim();
                helperString = helperString.substring(0, helperString.indexOf(" "));

                if (helperString.startsWith("LOG")) {
                    logEntryDTO.setUser(null);
                    logEntryDTO.setDatabase(null);
                } else {
                    String[] userDatabaseSplit = helperString.split("@");
                    logEntryDTO.setUser(userDatabaseSplit[0]);
                    logEntryDTO.setDatabase(userDatabaseSplit[1]);
                }

                int startLogContentIndex = logString.indexOf("@" + logEntryDTO.getDatabase()) + logEntryDTO.getDatabase().length() + 1;
                String content = (logString.substring(startLogContentIndex).trim() + "\n" + contentFromPrevious).trim();
                logEntryDTO.setContent(content);

                logEntryDTOList.add(logEntryDTO);
            }
        }
        Collections.reverse(logEntryDTOList);
        return logEntryDTOList;
    }

    private String getCurrentLogFileName() {
        String currentLogFilesPath = dbmsConfiguration.getCurrentLogFilesPath();
        String currentLogFileName = fileUtil.readStringFromFile(currentLogFilesPath).trim();
        int index = currentLogFileName.lastIndexOf(fileUtil.getFileSeparator());
        return currentLogFileName.substring(index + 1);
    }
}
