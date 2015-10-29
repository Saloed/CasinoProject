package accountService;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class DataBase {

    private static final String XML_FILE = "casinoServer/res/users.xml";

    private final class UserData {
        private Integer id;
        private String password;
        private Integer cash;

        public UserData(Integer id, String password, Integer cash) {
            this.id = id;
            this.cash = cash;
            this.password = password;
        }

        public Integer getId() {
            return id;
        }

        public Integer getCash() {
            return cash;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public int hashCode() {
            int hash = 17;
            hash = hash * id.hashCode();
            hash = hash * cash.hashCode();
            hash = hash * password.hashCode();
            return hash;
        }
    }

    private Map<String, UserData> users = new HashMap<>();

    private static final QName userTagName = new QName("user");
    private static final QName idAttribName = new QName("id");
    private static final QName nameAttribName = new QName("name");
    private static final QName passwordAttribName = new QName("password");
    private static final QName cashAttribName = new QName("cash");
    private XMLInputFactory factory = XMLInputFactory.newInstance();
    private Attribute id;
    private Attribute name;
    private Attribute password;
    private Attribute cash;

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
                UserData item = users.get(name);
                createNode(eventWriter, name, item.getId(), item.getPassword(), item.getCash());
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
                                   String pass, Integer cash) throws XMLStreamException {
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
        eventWriter.add(xmlEventFactory.createAttribute(new QName("cash"), cash.toString()));
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
            cash = element.getAttributeByName(cashAttribName);
        }
    }

    private void processEndElement(EndElement element) {
        if (element.getName().equals(userTagName)) {
            createUser();
            id = null;
            name = null;
            password = null;
            cash = null;
        }
    }

    private void createUser() {
        try {
            users.put(name.getValue(), new UserData(Integer.parseInt(id.getValue()),
                    password.getValue(),
                    Integer.parseInt(cash.getValue())));
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
            UserData temp = users.get(name);
            if (temp.getPassword().equals(password))
                return new Account(temp.getId(), name, temp.getPassword(), temp.getCash());
        }
        return new Account(0, name, password, 0);
    }

    public Account register(String name, String password) {
        if (users.containsKey(name))
            return new Account(0, name, password, 0);
        Account account = new Account(name, password);
        users.put(account.getName(), new UserData(account.getId(), account.getPassword(), account.getCash()));
        try {
            update(new File(XML_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public void updateAccount(Account account) {
        UserData temp = new UserData(account.getId(), account.getPassword(), account.getCash());
        users.replace(account.getName(), temp);
        try {
            update(new File(XML_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}