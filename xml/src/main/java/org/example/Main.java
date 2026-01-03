package org.example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    private Document document;
    private File xmlFile;
    private DefaultTableModel tableModel;
    private JTable booksTable;

    public Main() {
        setTitle("Управление библиотекой");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadXMLFile();
    }

    private void initComponents() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        JMenuItem loadItem = new JMenuItem("Загрузить XML");
        JMenuItem exitItem = new JMenuItem("Выход");

        loadItem.addActionListener(e -> loadXMLFile());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu bookMenu = new JMenu("Книги");
        JMenuItem addBookItem = new JMenuItem("Добавить книгу");
        JMenuItem searchAuthorItem = new JMenuItem("Поиск по автору");
        JMenuItem searchYearItem = new JMenuItem("Поиск по году");
        JMenuItem searchCategoryItem = new JMenuItem("Поиск по категории");
        JMenuItem updatePriceItem = new JMenuItem("Изменить цену");
        JMenuItem borrowBookItem = new JMenuItem("Выдать книгу");
        JMenuItem showAllItem = new JMenuItem("Показать все книги");

        addBookItem.addActionListener(e -> addNewBook());
        searchAuthorItem.addActionListener(e -> searchByAuthor());
        searchYearItem.addActionListener(e -> searchByYear());
        searchCategoryItem.addActionListener(e -> searchByCategory());
        updatePriceItem.addActionListener(e -> updateBookPrice());
        borrowBookItem.addActionListener(e -> borrowBook());
        showAllItem.addActionListener(e -> displayAllBooks());

        bookMenu.add(addBookItem);
        bookMenu.addSeparator();
        bookMenu.add(searchAuthorItem);
        bookMenu.add(searchYearItem);
        bookMenu.add(searchCategoryItem);
        bookMenu.addSeparator();
        bookMenu.add(updatePriceItem);
        bookMenu.add(borrowBookItem);
        bookMenu.addSeparator();
        bookMenu.add(showAllItem);

        menuBar.add(fileMenu);
        menuBar.add(bookMenu);
        setJMenuBar(menuBar);

        String[] columns = {"ID", "Название", "Автор", "Год", "Цена", "Категория",
                "Всего экз.", "Доступно", "Статус"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        booksTable = new JTable(tableModel);
        booksTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(booksTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Готово");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void loadXMLFile() {
        xmlFile = new File("D:/3 сем ПП/xml/src/main/resources/library.xml");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            File xsdFile = new File("D:/3 сем ПП/xml/src/main/resources/library.xsd");
            if (xsdFile.exists()) {
                try {
                    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                    Schema schema = schemaFactory.newSchema(xsdFile);
                    factory.setSchema(schema);
                } catch (SAXException e) {
                    System.out.println("Ошибка XSD: " + e.getMessage());
                }
            }

            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new org.xml.sax.ErrorHandler() {
                @Override
                public void warning(org.xml.sax.SAXParseException e) {
                    System.out.println("Предупреждение: " + e.getMessage());
                }

                @Override
                public void error(org.xml.sax.SAXParseException e) {
                    System.out.println("Ошибка валидации: " + e.getMessage());
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(Main.this,
                                "Ошибка валидации: " + e.getMessage(),
                                "Ошибка", JOptionPane.WARNING_MESSAGE);
                    });
                }

                @Override
                public void fatalError(org.xml.sax.SAXParseException e) {
                    System.out.println("Фатальная ошибка: " + e.getMessage());
                }
            });

            document = builder.parse(xmlFile);
//            System.out.println("Файл загружен успешно");
//            System.out.println("Найдено книг: " + document.getElementsByTagName("book").getLength());
            JOptionPane.showMessageDialog(this,
                    "XML прошёл проверку по схеме! Найдено книг: " +
                            document.getElementsByTagName("book").getLength(),
                    "Валидация успешна", JOptionPane.INFORMATION_MESSAGE);

            SwingUtilities.invokeLater(() -> {
                displayAllBooks();
                JOptionPane.showMessageDialog(this,
                        "XML файл успешно загружен! Книг: " +
                                document.getElementsByTagName("book").getLength(),
                        "Успех", JOptionPane.INFORMATION_MESSAGE);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка при загрузке XML: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void displayAllBooks() {
        System.out.println("Начало отображения книг...");
        tableModel.setRowCount(0);

        if (document != null) {
            NodeList bookNodes = document.getElementsByTagName("book");
            System.out.println("Найдено элементов book: " + bookNodes.getLength());

            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element book = (Element) bookNodes.item(i);
                System.out.println("Обработка книги " + (i+1));
                addBookToTable(book);
            }

            if (bookNodes.getLength() > 0) {
                System.out.println("Книги успешно добавлены в таблицу");
            } else {
                System.out.println("Книг не найдено");
            }
        } else {
            System.out.println("Документ не загружен");
        }
    }

    private void addBookToTable(Element book) {
        try {
            String id = book.getAttribute("id");
            String title = getElementText(book, "title");
            String author = getElementText(book, "author");
            String year = getElementText(book, "year");
            String price = getElementText(book, "price");
            String category = getElementText(book, "category");
            String totalCopies = book.getAttribute("totalCopies");
            String availableCopies = book.getAttribute("availableCopies");

            System.out.println("Добавляем книгу: " + title + " от " + author);

            String status = "Доступна";
            if (Integer.parseInt(availableCopies) == 0) {
                status = "Нет в наличии";
            } else if (Integer.parseInt(availableCopies) < Integer.parseInt(totalCopies)) {
                status = "Частично выдана";
            }

            tableModel.addRow(new Object[]{id, title, author, year, price, category,
                    totalCopies, availableCopies, status});

        } catch (Exception e) {
            System.out.println("Ошибка при добавлении книги в таблицу: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            Node node = nodes.item(0);
            if (node != null && node.getTextContent() != null) {
                return node.getTextContent().trim();
            }
        }
        return "";
    }

    private void addNewBook() {
        JDialog dialog = new JDialog(this, "Добавить новую книгу", true);
        dialog.setLayout(new GridLayout(9, 2));

        JTextField idField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField totalCopiesField = new JTextField();
        JTextField availableCopiesField = new JTextField();

        dialog.add(new JLabel("ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Название:"));
        dialog.add(titleField);
        dialog.add(new JLabel("Автор:"));
        dialog.add(authorField);
        dialog.add(new JLabel("Год издания:"));
        dialog.add(yearField);
        dialog.add(new JLabel("Цена:"));
        dialog.add(priceField);
        dialog.add(new JLabel("Категория:"));
        dialog.add(categoryField);
        dialog.add(new JLabel("Всего экземпляров:"));
        dialog.add(totalCopiesField);
        dialog.add(new JLabel("Доступно экземпляров:"));
        dialog.add(availableCopiesField);

        JButton addButton = new JButton("Добавить");
        JButton cancelButton = new JButton("Отмена");

        addButton.addActionListener(e -> {
            try {
                if (document == null) {
                    JOptionPane.showMessageDialog(dialog, "Документ не загружен");
                    return;
                }

                Element newBook = document.createElement("book");
                newBook.setAttribute("id", idField.getText());
                newBook.setAttribute("totalCopies", totalCopiesField.getText());
                newBook.setAttribute("availableCopies", availableCopiesField.getText());

                addChildElement(newBook, "title", titleField.getText());
                addChildElement(newBook, "author", authorField.getText());
                addChildElement(newBook, "year", yearField.getText());
                addChildElement(newBook, "price", priceField.getText());
                addChildElement(newBook, "category", categoryField.getText());

                Element root = document.getDocumentElement();
                if (root == null) {
                    root = document.createElement("library");
                    document.appendChild(root);
                }
                root.appendChild(newBook);

                saveXMLFile();
                addBookToTable(newBook);
                dialog.dispose();

                JOptionPane.showMessageDialog(this, "Книга успешно добавлена!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Ошибка: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        dialog.add(new JLabel());
        dialog.add(buttonPanel);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void addChildElement(Element parent, String tagName, String text) {
        Element element = document.createElement(tagName);
        element.setTextContent(text);
        parent.appendChild(element);
    }

    private void searchByAuthor() {
        String author = JOptionPane.showInputDialog(this, "Введите имя автора:");
        if (author != null && !author.trim().isEmpty()) {
            searchBooks("author", author);
        }
    }

    private void searchByYear() {
        String year = JOptionPane.showInputDialog(this, "Введите год издания:");
        if (year != null && !year.trim().isEmpty()) {
            searchBooks("year", year);
        }
    }

    private void searchByCategory() {
        String category = JOptionPane.showInputDialog(this, "Введите категорию:");
        if (category != null && !category.trim().isEmpty()) {
            searchBooks("category", category);
        }
    }

    private void searchBooks(String field, String value) {
        tableModel.setRowCount(0);

        if (document == null) {
            JOptionPane.showMessageDialog(this, "Документ не загружен");
            return;
        }

        NodeList bookNodes = document.getElementsByTagName("book");
        int count = 0;

        for (int i = 0; i < bookNodes.getLength(); i++) {
            Element book = (Element) bookNodes.item(i);
            String fieldValue = getElementText(book, field);

            if (fieldValue.toLowerCase().contains(value.toLowerCase())) {
                addBookToTable(book);
                count++;
            }
        }

        if (count == 0) {
            JOptionPane.showMessageDialog(this, "Книги не найдены");
        }
    }

    private void updateBookPrice() {
        String bookId = JOptionPane.showInputDialog(this, "Введите ID книги:");
        if (bookId == null || bookId.trim().isEmpty()) return;

        String newPrice = JOptionPane.showInputDialog(this, "Введите новую цену:");
        if (newPrice == null || newPrice.trim().isEmpty()) return;

        try {
            NodeList bookNodes = document.getElementsByTagName("book");
            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element book = (Element) bookNodes.item(i);
                if (book.getAttribute("id").equals(bookId)) {
                    NodeList priceNodes = book.getElementsByTagName("price");
                    if (priceNodes.getLength() > 0) {
                        priceNodes.item(0).setTextContent(newPrice);
                        saveXMLFile();
                        displayAllBooks();
                        JOptionPane.showMessageDialog(this, "Цена успешно обновлена!");
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Книга с таким ID не найдена!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
        }
    }

    private void borrowBook() {
        String bookId = JOptionPane.showInputDialog(this, "Введите ID книги для выдачи:");
        if (bookId == null || bookId.trim().isEmpty()) return;

        try {
            NodeList bookNodes = document.getElementsByTagName("book");
            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element book = (Element) bookNodes.item(i);
                if (book.getAttribute("id").equals(bookId)) {
                    int availableCopies = Integer.parseInt(book.getAttribute("availableCopies"));
                    if (availableCopies > 0) {
                        book.setAttribute("availableCopies", String.valueOf(availableCopies - 1));
                        saveXMLFile();
                        displayAllBooks();
                        JOptionPane.showMessageDialog(this,
                                "Книга успешно выдана! Осталось доступных: " + (availableCopies - 1));
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Нет доступных экземпляров этой книги!");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Книга с таким ID не найдена!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
        }
    }

    private void saveXMLFile() {
        try {
            if (document == null || xmlFile == null) {
                return;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (TransformerException e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}