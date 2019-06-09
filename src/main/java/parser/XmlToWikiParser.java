package parser;

import log.LoggingUtility;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class XmlToWikiParser implements WikiParser {
    private int currentLine = 0;
    private XMLEvent previousEvent = null;

    private XmlElementFormatter elementFormatter = new XmlElementFormatter();
    private XmlCharactersFormatter xmlCharactersFormatter = new XmlCharactersFormatter();


    @Override
    public String writeToWikiFormat(String filePath) {
        String wikiTextFormat = "";

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileReader(filePath));
            while (xmlEventReader.hasNext()) {
                wikiTextFormat = parseToWikiText(xmlEventReader, wikiTextFormat);
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            LoggingUtility.logError(this.getClass(), e.getMessage(), e);
        }

        clearFields();

        return wikiTextFormat.trim();
    }

    private String parseToWikiText(XMLEventReader reader, String wikiText) throws XMLStreamException {
        XMLEvent xmlEvent = reader.nextEvent();
        XMLEvent peekAtNext = reader.peek();

        if (xmlEvent.isStartElement()) {
            StartElementHelper helper = new StartElementHelper(xmlEvent, previousEvent, wikiText, currentLine);
            wikiText = elementFormatter.formatStart(helper);

            previousEvent = xmlEvent;
        }

        if (xmlEvent.isCharacters()) {
            wikiText += xmlCharactersFormatter.formatCharactersEvent(xmlEvent, previousEvent, peekAtNext);

            currentLine = xmlCharactersFormatter.getCurrentLine();
            previousEvent = xmlEvent;
        }

        if (xmlEvent.isEndElement()) {
            wikiText += elementFormatter.formatEndElement(xmlEvent);

            previousEvent = xmlEvent;
        }
        return wikiText;
    }

    private void clearFields() {
        currentLine = 0;
        previousEvent = null;
    }
}
