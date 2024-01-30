package pl.marifleur.microservice.postgres.linux.util.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ProcessUtil {

    public String run(String command) {
        StringBuilder output = new StringBuilder();
        BufferedReader inputBufferedReader = null;
        BufferedReader errorBufferedReader = null;
        ProcessBuilder processBuilder = new ProcessBuilder(
                "bash",
                "-l",
                "-c",
                command);
        try {
            Process process = processBuilder.start();

            inputBufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                String executionLine;
                while ((executionLine = inputBufferedReader.readLine()) != null) {
                    output.append(executionLine).append("\n");
                }
            } else {
                String errorLine;
                while ((errorLine = errorBufferedReader.readLine()) != null) {
                    output.append(errorLine).append("\n");
                }
            }

            return output.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (Objects.nonNull(inputBufferedReader)) {
                try {
                    inputBufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (Objects.nonNull(errorBufferedReader)) {
                try {
                    errorBufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String runAsUser(String username, String password, String command) {
        String fullCommand = "echo \"" + password + "\" | su -c \"" + command + "\" -m " + username;
        return run(fullCommand);
    }
}
