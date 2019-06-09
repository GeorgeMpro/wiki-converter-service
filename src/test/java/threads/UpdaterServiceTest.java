package threads;

import mocks.NewFileUtilityByFileTimeMock;
import mocks.XmlToWikiWriterMock;
import org.junit.jupiter.api.Test;
import processor.NewFileUtility;
import writer.WikiWriter;

import static org.junit.jupiter.api.Assertions.*;

class UpdaterServiceTest {

    private WikiWriter writer = new XmlToWikiWriterMock();
    private NewFileUtility utility = new NewFileUtilityByFileTimeMock();
    private UpdaterService service = new UpdaterService(utility, writer, "", "");

    @Test
    void whenShutDownThen_stopWorkingIsTrue() {
        boolean stopWorking = service.shutDown();

        assertTrue(stopWorking);
    }
}
