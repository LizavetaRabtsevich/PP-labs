package com.library.service;

import com.library.model.Book;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class XMLService {
    private static final String XML_FILE = "library.xml";
    private static final String XSD_FILE = "library.xsd";

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Document doc = parseXML();
            NodeList bookNodes = doc.getElementsByTagName("book");

            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element bookElement = (Element) bookNodes.item(i);
                Book book = parseBookElement(bookElement);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public void addBook(Book book) throws Exception {
        Document doc = parseXML();
        Element root = doc.getDocumentElement();

        // Создание нового элемента книги
        Element newBook = doc.createElement("book");
        newBook.setAttribute("id", UUID.randomUUID().toString());

        addElement(doc, newBook, "title", book.getTitle());
        addElement(doc, newBook, "author", book.getAuthor());
        addElement(doc, newBook, "category", book.getCategory());
        addElement(doc, newBook, "year", String.valueOf(book.getYear()));
        addElement(doc, newBook, "price", book.getPrice().toString());
        addElement(doc, newBook, "quantity", String.valueOf(book.getQuantity()));

        root.appendChild(newBook);
        saveDocument(doc);
    }

    public void updateBookPrice(String bookId, BigDecimal newPrice) throws Exception {
        Document doc = parseXML();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();

        String expression = String.format("//book[@id='%s']/price", bookId);
        XPathExpression expr = xpath.compile(expression);

        Node priceNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
        if (priceNode != null) {
            priceNode.setTextContent(newPrice.toString());
            saveDocument(doc);
        }
    }

    public void issueBook(String bookId) throws Exception {
        Document doc = parseXML();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();

        String expression = String.format("//book[@id='%s']/quantity", bookId);
        XPathExpression expr = xpath.compile(expression);

        Node quantityNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
        if (quantityNode != null) {
            int currentQuantity = Integer.parseInt(quantityNode.getTextContent());
            if (currentQuantity > 0) {
                quantityNode.setTextContent(String.valueOf(currentQuantity - 1));
                saveDocument(doc);
            }
        }
    }

    public List<Book> searchByAuthor(String author) throws Exception {
        return searchByXPath(String.format("//book[author='%s']", author));
    }

    public List<Book> searchByYear(int year) throws Exception {
        return searchByXPath(String.format("//book[year=%d]", year));
    }

    public List<Book> searchByCategory(String category) throws Exception {
        return searchByXPath(String.format("//book[category='%s']", category));
    }

    private List<Book> searchByXPath(String expression) throws Exception {
        List<Book> books = new ArrayList<>();
        Document doc = parseXML();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();

        XPathExpression expr = xpath.compile(expression);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {
            Element bookElement = (Element) nodes.item(i);
            Book book = parseBookElement(bookElement);
            books.add(book);
        }

        return books;
    }

    private Document parseXML() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        // Валидация по XSD
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(
                new File(getClass().getClassLoader().getResource(XSD_FILE).getFile()));
        factory.setSchema(schema);

        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(getClass()
                .getClassLoader().getResource(XML_FILE).getFile()));
    }

    private Book parseBookElement(Element bookElement) {
        Book book = new Book();
        book.setId(bookElement.getAttribute("id"));
        book.setTitle(getElementText(bookElement, "title"));
        book.setAuthor(getElementText(bookElement, "author"));
        book.setCategory(getElementText(bookElement, "category"));
        book.setYear(Integer.parseInt(getElementText(bookElement, "year")));
        book.setPrice(new BigDecimal(getElementText(bookElement, "price")));
        book.setQuantity(Integer.parseInt(getElementText(bookElement, "quantity")));
        return book;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }

    private void addElement(Document doc, Element parent,
                            String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private void saveDocument(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(
                new File(getClass().getClassLoader().getResource(XML_FILE).getFile()));
        transformer.transform(source, result);
    }
}