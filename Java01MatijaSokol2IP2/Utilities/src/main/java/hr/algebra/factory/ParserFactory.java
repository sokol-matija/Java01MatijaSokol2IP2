package hr.algebra.factory;

import java.io.InputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

public class ParserFactory {

    private ParserFactory() {
    }

    public static XMLEventReader createStaxParser(InputStream stream) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(stream);
        return eventReader;
    }
}
