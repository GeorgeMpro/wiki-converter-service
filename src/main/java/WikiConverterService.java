import log.LoggingUtility;
import processor.NewFileUtility;
import processor.NewFileUtilityByFileTime;
import threads.UpdaterService;
import writer.WikiWriter;
import writer.XmlToWikiWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static log.LoggingUtility.*;

public class WikiConverterService {

    private static final String RESOURCES = "src/test/resources/";
    private static final String LOG_FILE = RESOURCES + "log/error.log";

    private static NewFileUtility utility = new NewFileUtilityByFileTime();
    private static WikiWriter writer = new XmlToWikiWriter();
    private static String inputFolder;
    private static String outputFolder;

    public static void main(String[] args) throws IOException {

        LoggingUtility loggingUtility = new LoggingUtility(LOG_FILE);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            runWikiWriterService(br);

        } catch (IOException | NullPointerException exc) {
            logError(WikiConverterService.class, exc.getMessage(), exc);
        } finally {
            loggingUtility.closeLoggingResources();
        }
    }

    private static void runWikiWriterService(BufferedReader br) throws IOException {
        setDirectoryPath(br);

        UpdaterService updaterService = startServiceThread();

        System.out.println("Press Q to Quit the service");
        while (true) {

            String in = br.readLine();
            if (in.equalsIgnoreCase("Q")) {
                updaterService.shutDown();
                System.out.println("Shutting down...");
                break;
            }
        }
    }

    private static UpdaterService startServiceThread() {
        UpdaterService updaterService = new UpdaterService(utility, writer, inputFolder, outputFolder);
        new Thread(updaterService, "Update Service").start();
        return updaterService;
    }

    /**
     * Reads and sets input and output directories the input source.
     *
     * @param bufferedReader
     * @throws IOException
     */
    private static void setDirectoryPath(BufferedReader bufferedReader) throws IOException {
        System.out.println("Enter input folder:");
        inputFolder = bufferedReader.readLine();
        System.out.println("Enter wiki output folder:");
        outputFolder = bufferedReader.readLine();
    }
}
