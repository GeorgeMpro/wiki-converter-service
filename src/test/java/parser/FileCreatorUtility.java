package parser;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

/**
 * Utility class for setting up XML test files.
 */
class FileCreatorUtility {

    static Document convertStringToDocument(String xmlStr) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();

            return builder.parse(new InputSource(new StringReader(xmlStr)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static void writeDocToTestXmlFile(String filePath, Document doc) throws IOException, TransformerException {
        DOMSource source = new DOMSource(doc);
        FileWriter writerToFile = new FileWriter(new File(filePath));
        StreamResult result = new StreamResult(writerToFile);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
    }

}
