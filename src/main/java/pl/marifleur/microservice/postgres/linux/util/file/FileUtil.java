package pl.marifleur.microservice.postgres.linux.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class FileUtil {

    public File createFile(String... paths) {
        String filePath = buildPath(paths);

        File file = new File(filePath);

        try {
            if (file.createNewFile()) {
                return file;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File createDirectory(String... paths) {
        String filePath = buildPath(paths);
        Path directoryPath = Paths.get(filePath);

        try {
            Files.createDirectory(directoryPath);
            return new File(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileSeparator() {
        return File.separator;
    }

    public BasicFileAttributes getBaseFileAttributes(String... paths) {
        Path filePath = Paths.get(buildPath(paths));

        try {
            return Files.readAttributes(filePath, BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String buildPath(String... paths) {
        StringBuilder filePath = new StringBuilder();

        for (String path : paths) {
            if (!filePath.toString().isEmpty()) {
                filePath.append(File.separator).append(path);
            } else {
                filePath.append(path);
            }
        }

        return filePath.toString();
    }

    public boolean fileExists(String... paths) {
        String filePath = buildPath(paths);

        File file = new File(filePath);

        return file.exists();
    }

    public void writeStringToFile(String content, String... paths) {
        String filePath = buildPath(paths);

        try {
            Files.write(Path.of(filePath), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readStringFromFile(String... paths) {
        String filePath = buildPath(paths);

        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readStringFromFile(File file) {
        Path path = Path.of(file.getPath());
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File getFile(String... paths) {
        String filePath = buildPath(paths);

        return new File(filePath);
    }

    public String getParentPath(String... paths) {
        File file = getFile(paths);
        return file.getParent();
    }

    public File[] getChildrenFiles(String... paths) {
        File parentFile = getFile(paths);
        return parentFile.listFiles();
    }

    public void copyDirectory(String sourcePath, String destinationPath) {
        BufferedReader errorBufferedReader = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "bash",
                    "-l",
                    "-c",
                    "cp -r " + sourcePath + " " + destinationPath);

            Process process = processBuilder.start();
            errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            int exitCode = process.waitFor();
            System.out.println("\"" + "cp -r " + sourcePath + " " + destinationPath + "\" exited with status code: " + exitCode);

            if (exitCode != 0) {
                String errorLine;
                while ((errorLine = errorBufferedReader.readLine()) != null) {
                    System.out.println(errorLine);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (Objects.nonNull(errorBufferedReader)) {
                try {
                    errorBufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
