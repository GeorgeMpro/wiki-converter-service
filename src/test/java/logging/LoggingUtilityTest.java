package logging;

import log.LoggingUtility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static helper.TestFileHelper.RESOURCES;
import static log.LoggingUtility.logError;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggingUtilityTest {

    private static final String logFileLocation = RESOURCES + "log/loggingTest.log";
    private static LoggingUtility loggingUtility;
    private File file = new File(logFileLocation);

    @BeforeAll
    static void setUP() throws Exception {
        loggingUtility = new LoggingUtility(logFileLocation);
    }

    @AfterAll
    static void tearDown() {
        loggingUtility.closeLoggingResources();
    }


    @Test
    void whenLoggingUtilityInitialized_thenLogFileExists() {
        assertTrue(file.exists());
    }

    @Test
    void whenLoggingError_thenMessageIsWritten() {

        logError(LoggingUtilityTest.class, "Test Message", new Exception("Test"));

        long fileLength = file.length();
        assertNotEquals(0, fileLength);
    }

    @Test
    void whenCloseLoggingResources_thenFileHandlerIsClosed() {
        assertTrue(loggingUtility.closeLoggingResources());
    }
}
