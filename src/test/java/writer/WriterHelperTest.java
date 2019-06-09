package writer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WriterHelperTest {

    private WriterHelper helper = new WriterHelper();

    @Test
    void changeFileExtension() {
        assertFileNameChange("f1.wiki", "f1.xml");
        assertFileNameChange("f2.wiki", "f2.xml");
        assertFileNameChange("f.file.files.wiki", "f.file.files.xml");
    }

    @Test
    void whenEmptyFileNameThrowException() {
        String fileName = "";
        Throwable throwable = assertThrows(WriterHelper.InvalidFileNameException.class, () -> helper.changeFileExtension(fileName));

        assertEquals("Invalid file name:[" + fileName + "]", throwable.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"f1.xml", "f2.xml", "f3.xml"})
    void getFileNameFromPath(String name) {
        String directory = "/test/files/";
        assertEquals(name, helper.extractFileName(directory, directory + name));
    }


    private void assertFileNameChange(String expected, String fileName) {
        String actual = helper.changeFileExtension(fileName);
        assertEquals(expected, actual);
    }

}
