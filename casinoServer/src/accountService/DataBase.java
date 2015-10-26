package accountService;

import javafx.util.Pair;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class DataBase {

    private static final String XML_FILE = "casinoServer/res/users.xml";

    private Map<String, Pair<Integer, String>> users = new HashMap<>();

    private static final QName userTagName = new QName("user");
    private static final QName idAttribName = new QName("id");
    private static final QName nameAttribName = new QName("name");
    private static final QName passwordAttribName = new QName("password");
    private XMLInputFactory factory = XMLInputFactory.newInstance();
    private Attribute id;
    private Attribute name;
    private Attribute password;

    public DataBase() {
        try {
            parse(new File(XML_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(File file) throws IOException, XMLStreamException {
        if (!file.canWrite())
            throw new IOException("Cant write to file");
        try {
            FileOutputStream out = new FileOutputStream(file);
            XMLEventWriter eventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(out, "UTF-8");

            XMLEvent end = XMLEventFactory.newInstance().createDTD("\n");

            StartDocument startDocument = XMLEventFactory.newInstance().createStartDocument();

            eventWriter.add(startDocument);
            eventWriter.add(end);

            StartElement configStartElement = XMLEventFactory.newInstance().createStartElement("", "", "users");
            eventWriter.add(configStartElement);
            eventWriter.add(end);

            Set<String> userSet = users.keySet();

            for (String name : userSet) {
                Pair<Integer, String> item = users.get(name);
                createNode(eventWriter, name, item.getKey(), item.getValue());
            }
            eventWriter.add(XMLEventFactory.newInstance().createEndElement("", "", ""));
            eventWriter.add(end);
            eventWriter.add(XMLEventFactory.newInstance().createEndDocument());
            eventWriter.close();

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

    }

    private static void createNode(XMLEventWriter eventWriter, String name, Integer id,
                                   String pass) throws XMLStreamException {
        XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
        XMLEvent end = xmlEventFactory.createDTD("\n");
        XMLEvent tab = xmlEventFactory.createDTD("\t");
        //Create Start node
        StartElement sElement = xmlEventFactory.createStartElement("", "", "user");
        eventWriter.add(tab);
        eventWriter.add(sElement);

        eventWriter.add(xmlEventFactory.createAttribute(new QName("name"), name));
        eventWriter.add(xmlEventFactory.createAttribute(new QName("id"), id.toString()));
        eventWriter.add(xmlEventFactory.createAttribute(new QName("password"), pass));

        // Create End node
        EndElement eElement = xmlEventFactory.createEndElement("", "", "user");
        eventWriter.add(eElement);
        eventWriter.add(end);

    }

    private void parse(File file) throws IOException, XMLStreamException {
        if (!file.canRead())
            throw new IOException("Cant read file");
        FileInputStream in = new FileInputStream(file);
        XMLEventReader eventReader = factory.createXMLEventReader(in);
        while (eventReader.hasNext()) {
            XMLEvent xmlEvent = eventReader.nextEvent();
            switch (xmlEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    processStartElement(xmlEvent.asStartElement());
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    processEndElement(xmlEvent.asEndElement());
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    cleanup();
                    break;
            }
        }
    }

    private void processStartElement(StartElement element) {
        if (element.getName().equals(userTagName)) {
            id = element.getAttributeByName(idAttribName);
            name = element.getAttributeByName(nameAttribName);
            password = element.getAttributeByName(passwordAttribName);
        }
    }

    private void processEndElement(EndElement element) {
        if (element.getName().equals(userTagName)) {
            createUser();
            id = null;
            name = null;
            password = null;
        }
    }

    private void createUser() {
        try {
            users.put(name.getValue(), new Pair<>(Integer.parseInt(id.getValue()), password.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cleanup() {
        id = null;
        name = null;
        password = null;
    }


    public Account authenticate(String name, String password) {
        if (users.containsKey(name)) {
            Pair<Integer, String> temp = users.get(name);
            return new Account(temp.getKey(), name, temp.getValue());
        }
        return new Account(0,name,password);
    }

    public boolean register(String name, String password) {
        Account temp = new Account(name, password);

        users.put(temp.getName(), new Pair<>(temp.getId(), temp.getPassword()));
        try {
            update(new File(XML_FILE));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}