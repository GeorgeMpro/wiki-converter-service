package log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.util.logging.Level.WARNING;

/**
 * A utility class for logging messages to file.
 */
public final class LoggingUtility {

    private final static Logger logger = Logger.getLogger("LoggingUtility");
    private FileHandler handler;

    public LoggingUtility(String errorLogFileLocation) throws IOException {
        setUpFileHandler(errorLogFileLocation);
    }

    /**
     * Setup logger's handler.
     *
     * @param errorLogFileLocation the location of the log file
     * @throws IOException
     */
    private void setUpFileHandler(String errorLogFileLocation) throws IOException {
        handler = new FileHandler(errorLogFileLocation);
        logger.addHandler(handler);
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
    }

    /**
     * Write error log to file.
     *
     * @param theClass  the class where the error occurred
     * @param message   error message
     * @param throwable the thrown error
     */
    public static void logError(Class theClass, String message, Throwable throwable) {
        logger.log(WARNING, message, throwable);
    }

    /**
     * Tries to close resources used by {@link LoggingUtility}
     *
     * @return true - if the resources were closed.
     */
    public boolean closeLoggingResources() {
        if (handler != null) {
            handler.close();
            return true;
        } else
//            todo throw exception?
            return false;
    }
}

