package threads;

import processor.InputFileProcessor;
import processor.NewFileUtility;
import writer.WikiWriter;

import java.io.IOException;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static log.LoggingUtility.logError;

public class UpdaterService implements Runnable {

    private InputFileProcessor fileProcessor;
    private final String inputPath;
    private final String outputPath;
    private volatile boolean stopWorking = false;
    private int sleepTime = 1000;


    public UpdaterService(NewFileUtility utility, WikiWriter writer, String theInputPath, String theOutputPath) {
        fileProcessor = new InputFileProcessor(utility, writer);
        inputPath = theInputPath;
        outputPath = theOutputPath;
    }

    /**
     * Set time for the service to sleep after checking for new files in input folder.<br>
     * There is a default time when none is set.
     *
     * @param sleepTime time in milliseconds
     */
    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        while (!stopWorking) {
            checkForNew(sleepTime);
        }
//        todo(?) del
        System.out.println("Shut down " + currentThread().getName());
    }

    private void checkForNew(int sleepTime) {
        try {
            fileProcessor.checkAndUpdateDirectory(inputPath, outputPath);
            sleep(sleepTime);
        } catch (IOException | InterruptedException e) {
            logError(UpdaterService.class, e.getMessage(), e);
        }
    }

    public synchronized boolean shutDown() {
        return stopWorking = true;
    }
}
