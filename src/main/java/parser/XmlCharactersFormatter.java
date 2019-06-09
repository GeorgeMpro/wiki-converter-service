package parser;

import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

class XmlCharactersFormatter {
    private int currentLine = 0;

    int getCurrentLine() {
        return this.currentLine;
    }

    String formatCharactersEvent(XMLEvent current, XMLEvent previous, XMLEvent next) {
        Characters characters = current.asCharacters();
        currentLine = characters.getLocation().getLineNumber();

        return formatCharactersData(characters, previous, next);
    }

    private String formatCharactersData(Characters characters, XMLEvent previous, XMLEvent next) {
        String data = characters.getData();
        String charFormat = "";

        boolean isWhitespace = characters.isWhiteSpace();
        if (!isWhitespace)
            charFormat += formatDataWithText(previous, next, data);

        return charFormat;
    }

    private String formatDataWithText(XMLEvent previous, XMLEvent next, String data) {
        boolean hasNewline = data.contains("\n");
        if (shouldTrimAndSpace(previous, next)) {
            data = data.trim() + " ";
        } else if (shouldAddSpaceBeforeAndNewLineAfter(previous, next)) {
            data = " " + data.trim() + "\n";
        } else if (hasNewline) {
            data = data.trim() + "\n";
        }

        return data;
    }

    private boolean shouldAddSpaceBeforeAndNewLineAfter(XMLEvent previous, XMLEvent next) {
        boolean isPrevEnd = previous.isEndElement();
        boolean isNextChars = next.isCharacters();
        if (isPrevEnd && !isNextChars) {
            String prevName = previous.asEndElement().getName().toString();

            return !prevName.equals("section");
        }
        return false;
    }

    private boolean shouldTrimAndSpace(XMLEvent previous, XMLEvent next) {
        boolean isPrevStart = previous.isStartElement();
        boolean isNextStart = next.isStartElement();
        if (isPrevStart && isNextStart) {
            String nextName = next.asStartElement().getName().toString();

            return !nextName.equals("section");
        }
        return false;
    }
}
