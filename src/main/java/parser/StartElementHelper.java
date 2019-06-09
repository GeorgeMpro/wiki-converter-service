package parser;

import javax.xml.stream.events.XMLEvent;

class StartElementHelper {
    private XMLEvent current;
    private XMLEvent previous;
    private String textToFormat;
    private int currentLine;

    public StartElementHelper(XMLEvent current, XMLEvent previous, String textToFormat, int currentLine) {
        this.current = current;
        this.previous = previous;
        this.textToFormat = textToFormat;
        this.currentLine = currentLine;
    }

    public XMLEvent getCurrent() {
        return current;
    }

    public XMLEvent getPrevious() {
        return previous;
    }

    public String getTextToFormat() {
        return textToFormat;
    }

    public int getCurrentLine() {
        return currentLine;
    }
}
