package ru.nd.loggers;

import org.apache.commons.io.FileUtils;
import ru.nd.beans.Event;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileEventLogger implements EventLogger {
    private String filename;
    private File file;

    public FileEventLogger() {}

    public FileEventLogger(String filename) {
        this.filename = filename;
    }

    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.toString() + "\n", "UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return "";
    }

    public void init() throws IOException {
        this.file = new File(filename);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Can not create file");
            }
        }
        if (!file.canWrite()) {
            throw new IOException("File inaccessible for writing");
        }
    }
}
