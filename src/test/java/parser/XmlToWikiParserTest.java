package parser;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlToWikiParserTest {

    private WikiParser writer = new XmlToWikiParser();
    private String filePath = "src/test/resources/test.xml";

    @Test
    void readTextOutsideTags() throws Exception {
        assertXmlWriteToWiki("", "<report>\n</report>");
        assertXmlWriteToWiki("text", "<report>\n    text\n</report>\n");
        assertXmlWriteToWiki("text", "<report>\n    text\n    <section>\n    </section>\n</report>\n");
        assertXmlWriteToWiki("text\ntext", "<report>\n    text\n    <section>\n        text\n    </section>\n</report>\n");
        assertXmlWriteToWiki("text\ntext\ntext", "<report>\n    text\n    <section>\n        text\n    </section>\n    text\n</report>\n");
        assertXmlWriteToWiki("text\ntext\ntext\ntext\ntext", "<report>\n    text\n    <section>\n        text\n    </section>\n    text\n    <section>\n        text\n        <section>\n            text\n        </section>\n    </section>\n</report>\n");
        assertXmlWriteToWiki("text1\ntext2\ntext3\ntext4\ntext5\ntext6", "<report>\n    text1\n    <section>\n        text2\n    </section>\n    text3\n    <section>\n        text4\n        <section>\n            text5\n            <section>\n                text6\n            </section>\n        </section>\n    </section>\n</report>\n");
        assertXmlWriteToWiki("text1\ntext2\ntext3\ntext4\ntext5\ntext6\ntext7\ntext8\ntext9", "<report>\n    text1\n    <section>\n       text2\n    </section>\n    text3\n    <section>\n        text4\n        <section>\n            text5\n            <section>\n                text6\n            </section>\n        </section>\n    </section>\n    text7\n    <section>\n        text8\n        <section>\n            text9\n        </section>\n    </section>\n</report>\n");
        assertXmlWriteToWiki("text1\ntext2\ntext3\ntext4\ntext5\ntext6\ntext7\ntext8\ntext9\ntext10\ntext11\ntext12", "<report>\n    text1\n    <section>\n        text2\n    </section>\n    text3\n    <section>\n        text4\n        <section>\n            text5\n            <section>\n                text6\n            </section>\n        </section>\n    </section>\n    text7\n    <section>\n        text8\n        <section>\n            text9\n            <section>\n                text10\n                <section>text11\n                    <section>\n                        text12\n                    </section>\n                </section>\n            </section>\n        </section>\n    </section>\n</report>\n");
        assertXmlWriteToWiki("text1\ntext2\ntext3\ntext4\ntext5\ntext6\ntext7\ntext8\ntext9\ntext10\ntext11\ntext12\ntext13\ntext14\ntext15", "<report>\n   text1\n    <section>\n        text2\n    </section>\n    text3\n    <section>\n        text4\n        <section>\n            text5\n            <section>\n                text6\n            </section>\n        </section>\n    </section>\n    text7\n    <section>\n        text8\n        <section>\n            text9\n            <section>\n                text10\n                <section>text11\n                    <section>\n                        text12\n                        <section>\n                            text13\n                        </section>\n                        text14\n                        <section>\n                            text15\n                        </section>\n                    </section>\n                </section>\n            </section>\n        </section>\n    </section>\n</report>\n");
    }

    @Test
    void sectionHeadingFormatting() throws Exception {
        assertXmlWriteToWiki("=Text=", "<report>\n" +
                "    <section heading=\"Text\"></section>\n" +
                "</report>");
        assertXmlWriteToWiki("=Text1=\n=Text2=", "<report>\n    <section heading=\"Text1\"></section>\n    <section heading=\"Text2\"></section>\n</report>\n");
        assertXmlWriteToWiki("=Text1=\n=Text2=\n==Text3==", "<report>\n    <section heading=\"Text1\"></section>\n    <section heading=\"Text2\">\n        <section heading=\"Text3\"></section>\n    </section>\n</report>\n");
        assertXmlWriteToWiki("=Text1=\n=Text2=\n==Text3==\n===Text4===\n====Text5====", "<report>\n    <section heading=\"Text1\"></section>\n    <section heading=\"Text2\">\n        <section heading=\"Text3\">\n            <section heading=\"Text4\">\n                <section heading=\"Text5\"></section>\n            </section>\n        </section>\n    </section>\n</report>\n");
        assertXmlWriteToWiki("=Text1=\n=Text2=\n==Text3==\n===Text4===\n====Text5====\n====Text6====\n====Text7====\n=====Text8=====",
                "<report>\n    <section heading=\"Text1\"></section>\n    <section heading=\"Text2\">\n        <section heading=\"Text3\">\n            <section heading=\"Text4\">\n                <section heading=\"Text5\"></section>\n                <section heading=\"Text6\"></section>\n                <section heading=\"Text7\">\n                    <section heading=\"Text8\"></section>\n                </section>\n            </section>\n        </section>\n    </section>\n</report>\n");
    }
//    TODO add test - throw exception on sixth heading

    @Test
    void readBold() throws Exception {
        assertXmlWriteToWiki("", "<report></report>");
        assertXmlWriteToWiki("text", "<report>text</report>");
        assertXmlWriteToWiki("'''text'''", "<report><bold>text</bold></report>");
        assertXmlWriteToWiki("'''text1''' text2", "<report><bold>text1</bold>text2</report>");
        assertXmlWriteToWiki("'''text1''' text2\n'''text3'''", "<report><bold>text1</bold>text2<bold>text3</bold></report>");
        assertXmlWriteToWiki("=com.company.api.data=\n'''Passed:''' All passed!\n'''Failed:''' None", "<section heading=\"com.company.api.data\">\n    <bold>Passed:</bold>\n    All passed!\n    <bold>Failed:</bold>\n    None\n</section>\n");
    }

    @Test
    void readItalic() throws Exception {
        assertXmlWriteToWiki("text", "<report>text</report>");
        assertXmlWriteToWiki("''text''", "<report><italic>text</italic></report>");
        assertXmlWriteToWiki("''text1'' text2", "<report><italic>text1</italic>text2</report>");
        assertXmlWriteToWiki("''text1'' text2\n''text3''", "<report><italic>text1</italic>text2<italic>text3</italic></report>");
        assertXmlWriteToWiki("''text1'' text2\n''text3''''text''", "<report><italic>text1</italic>text2<italic>text3</italic>\n<italic>text</italic></report>");
    }

    @Test
    void readBoldAndItalic() throws Exception {
        assertXmlWriteToWiki("'''text1'''''text2''", "<report><bold>text1</bold><italic>text2</italic></report>");
        assertXmlWriteToWiki("'''''text'''''", "<report><bold><italic>text</italic></bold></report>");
        assertXmlWriteToWiki("'''''text''''' text", "<report><bold><italic>text</italic></bold>text</report>");
        assertXmlWriteToWiki("'''''text''''' text\n'''text3'''", "<report><bold><italic>text</italic></bold>text\n<bold>text3</bold></report>");
        assertXmlWriteToWiki("'''''text''''' text\n'''text3'''\n'''text4'''''text5''", "<report>\n    <bold>\n        <italic>text</italic>\n    </bold>\n    text\n    <bold>text3</bold>\n    <section>\n        <bold>text4</bold>\n        <italic>text5</italic>\n    </section>\n</report>");
        assertXmlWriteToWiki("'''''text1''''' text2\n'''text3'''\n=Bold and Italic=\n'''text4'''''text5''", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<report>\n    <bold>\n        <italic>text1</italic>\n    </bold>\n    text2\n    <bold>text3</bold>\n    <section heading=\"Bold and Italic\">\n        <bold>text4</bold>\n        <italic>text5</italic>\n    </section>\n</report>");
    }

    @Test
    void checkForSectionsAndHeadings() throws Exception {
        assertXmlWriteToWiki("=Test1=", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<report>\n    <section>\n    </section>\n    <section heading=\"Test1\">\n</section>\n</report>");
        assertXmlWriteToWiki("=Test1=\n==Test2==\n=Test3=", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><report>\n    <section>\n    </section>\n    <section heading=\"Test1\">\n        <section heading=\"Test2\"></section>\n</section>\n    <section heading=\"Test3\"></section>\n</report>");

    }

    @Test
    void sectionHeadingStartAtNewLine() throws Exception {
        assertXmlWriteToWiki("'''''text''''' text\n'''text3'''\n'''text4'''''text5''", "<report>\n    <bold>\n        <italic>text</italic>\n    </bold>\n    text\n    <bold>text3</bold>\n    <section>\n        <bold>text4</bold>\n        <italic>text5</italic>\n    </section>\n</report>");
    }

    @Test
    void noEmptyLineBetweenSections() throws Exception {
        assertXmlWriteToWiki("=Preparing DB=\nDone in ''1556ms''\n=JUnits=\n==com.company.api.data==\n'''Passed:''' All passed!\n'''Failed:''' None",
                "<report>\n    <section heading=\"Preparing DB\">\n        Done in\n        <italic>1556ms</italic>\n    </section>\n    <section heading=\"JUnits\">\n        <section heading=\"com.company.api.data\">\n            <bold>Passed:</bold>\n            All passed!\n            <bold>Failed:</bold>\n            None\n        </section>\n    </section>\n</report>\n");
    }

    @Test
    void testExampleOne() throws Exception {
        assertXmlWriteToWiki("The text can start outside a section....\n=Build 1234=\n==Api component==\n'''Date: ''01.04.2015'''''\n===Main===\n====company.jar====\n''Built in '''512ms'''''\n===Test===\nTest performed on the different databases\n====Sybase====\n=====Preparing DB=====\nDone in ''1556ms''\n=====JUnits=====\n======com.company.api.data======\n'''Passed:''' All passed!\n'''Failed:''' None",
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<report>\n    The text can start outside a section....\n\n    <section heading=\"Build 1234\">\n        <section heading=\"Api component\">\n            <bold>Date: <italic>01.04.2015</italic></bold>\n            <section heading=\"Main\">\n                <section heading=\"company.jar\">\n                    <italic>Built in <bold>512ms</bold></italic>\n                </section>\n            </section>\n            <section heading=\"Test\">\n                Test performed on the different databases\n                <section heading=\"Sybase\">\n                    <section heading=\"Preparing DB\">\n                        Done in\n                        <italic>1556ms</italic>\n                    </section>\n                    <section heading=\"JUnits\">\n                        <section heading=\"com.company.api.data\">\n                            <bold>Passed:</bold> All passed!\n                            <bold>Failed:</bold> None\n                        </section>\n                    </section>\n                </section>\n            </section>\n        </section>\n    </section>\n</report>\n");

    }

    @Test
    void testExampleTwo() throws Exception {
        assertXmlWriteToWiki("The text can start outside a section....\n=Build 1234=\n==Api component==\n'''Date: ''01.04.2015'''''\n===Main===\n====company.jar====\n''Built in '''512ms'''''\n===Test===\nTest performed on the different databases\n====Sybase====\n=====Preparing DB=====\nDone in ''1556ms''\n=====JUnits=====\n======com.company.api.data======\n'''Passed:''' 5 passed!\n'''Failed:''' 1 failed!\n=======failed tests=======\n'''TestInterview'''", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<report>\n    The text can start outside a section....\n\n    <section heading=\"Build 1234\">\n        <section heading=\"Api component\">\n            <bold>Date:\n                <italic>01.04.2015</italic>\n            </bold>\n            <section heading=\"Main\">\n                <section heading=\"company.jar\">\n                    <italic>Built in\n                        <bold>512ms</bold>\n                    </italic>\n                </section>\n            </section>\n            <section heading=\"Test\">\n                Test performed on the different databases\n                <section heading=\"Sybase\">\n                    <section heading=\"Preparing DB\">\n                        Done in\n                        <italic>1556ms</italic>\n                    </section>\n                    <section heading=\"JUnits\">\n                        <section heading=\"com.company.api.data\">\n                            <bold>Passed:</bold>\n                            5 passed!\n                            <bold>Failed:</bold>\n                            1 failed!\n                            <section heading=\"failed tests\">\n                                <bold>TestInterview</bold>\n                            </section>\n                        </section>\n                    </section>\n                </section>\n            </section>\n        </section>\n    </section>\n</report>\n");
    }

    private void assertXmlWriteToWiki(String expected, String xmlStr) throws IOException, TransformerException {

        Document doc = FileCreatorUtility.convertStringToDocument(xmlStr);
        FileCreatorUtility.writeDocToTestXmlFile(filePath, doc);

        String actual = writer.writeToWikiFormat(filePath);

        assertEquals(expected, actual);
    }
}
