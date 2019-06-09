package parser;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.HashMap;
import java.util.Iterator;

class XmlElementFormatter {
    private int currentLine;

    private int sectionCounter = 0;
    private int headingCounter = 0;
    private HashMap<Integer, Integer> sectionHeadingCountMapper = new HashMap<>();

    public String formatStart(StartElementHelper helper) {
        this.currentLine = helper.getCurrentLine();
        String s = helper.getTextToFormat();
        XMLEvent current = helper.getCurrent();
        XMLEvent previous = helper.getPrevious();

        if (shouldTrimBeforeEvent(current, previous))
            s = s.trim();

        s += formatStartElement(current);
        return s;
    }

    private boolean shouldTrimBeforeEvent(XMLEvent current, XMLEvent previous) {
        boolean isSection = isStartASection(current.asStartElement());
        boolean shouldBeTrimmedBeforeSection;

        if (previous != null)
            shouldBeTrimmedBeforeSection = followedBy(previous);
        else
            return false;

        return isSection && shouldBeTrimmedBeforeSection;
    }

    private boolean isStartASection(StartElement element) {

        return element.getName().toString().equals("section");
    }

    private boolean followedBy(XMLEvent previous) {
        if (previous.isCharacters())
            return true;

        if (previous.isStartElement()) {
            String startName = previous.asStartElement().getName().toString();
            return startName.equals("section");
        }

        return false;
    }

    private String formatStartElement(XMLEvent xmlEvent) {

        StartElement start = xmlEvent.asStartElement();
        String startName = start.getName().toString();
        String startFormat = "";


        boolean isStartASection = startName.equals("section");
        if (isStartASection) {
            startFormat += setupSectionStart(start);
            startFormat += checkAttributeValues(start);
        } else
            startFormat = formatByTagValue(startFormat, startName);

        return startFormat;
    }

    private String setupSectionStart(StartElement start) {
        sectionCounter++;

        int sectionLineNumber = start.getLocation().getLineNumber();
        boolean doesTextOverlapSection = (sectionLineNumber == currentLine);

        return doesTextOverlapSection ? "\n" : "";
    }

    private String checkAttributeValues(StartElement start) {
        StringBuilder attributeString = new StringBuilder();
        Iterator<Attribute> attributes = start.getAttributes();

        while (attributes.hasNext()) {
            Attribute attribute = attributes.next();
            String name = attribute.getName().toString();
            String value = attribute.getValue();

            boolean doesSectionHaveAHeading = name.equals("heading");
            if (doesSectionHaveAHeading)
                attributeString.append(setupHeading(value));
        }

        return attributeString.toString();
    }

    private String setupHeading(String value) {
        headingCounter++;
        sectionHeadingCountMapper.put(sectionCounter, headingCounter);

        return formatHeadingValue(value) + "\n";
    }

    private String formatHeadingValue(String attributeValue) {
        StringBuilder stringBuilder = new StringBuilder(attributeValue);

        for (int j = 0; j < headingCounter; j++)
            stringBuilder.insert(0, "=").append("=");

        return stringBuilder.toString();
    }

    public String formatEndElement(XMLEvent current) {
        EndElement end = current.asEndElement();
        String formatEnd = "";

        String endName = end.getName().toString();
        formatEnd = formatByTagValue(formatEnd, endName);

        boolean isAtSectionEnd = endName.equals("section");
        if (isAtSectionEnd)
            setupSectionEnd();

        return formatEnd;
    }

    private String formatByTagValue(String string, String tagName) {
        if (tagName.equals("bold")) {
            string += "'''";
        } else if (tagName.equals("italic")) {
            string += "''";
        }
        return string;
    }

    private void setupSectionEnd() {
        Integer sectionHeadingCounter = sectionHeadingCountMapper.get(sectionCounter);
        boolean doesSectionHaveHeader = sectionHeadingCounter != null;
        if (doesSectionHaveHeader) {
            sectionHeadingCountMapper.remove(sectionCounter);
            headingCounter--;
        }
        sectionCounter--;
    }
}
