package org.example;

import javax.swing.*;
import java.awt.*;

public class SortingFrame extends JFrame {
    private JPanel controlPanel;
    private JPanel mainPanel;
    private JComboBox<String> algorithmCombo;
    private JButton startButton;
    private JButton randomizeButton;
    private JButton resetButton;
    private JTextField speedField;

    public JPanel getMainPanel() { return mainPanel; }
    public JComboBox<String> getAlgorithmCombo() { return algorithmCombo; }
    public JButton getStartButton() { return startButton; }
    public JButton getRandomizeButton() { return randomizeButton; }
    public JButton getResetButton() { return resetButton; }
    public JTextField getSpeedField() { return speedField; }

    public SortingFrame() {
        createUI();
        setupFrame();
    }

    private void createUI() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 10));
        controlPanel.setBackground(new Color(26, 26, 26));
        JLabel algorithmLabel = new JLabel("Алгоритм:");
        algorithmLabel.setForeground(Color.WHITE);
        algorithmLabel.setFont(new Font("Arial", Font.BOLD, 14));

        algorithmCombo = new JComboBox<>(new String[]{
                "Пузырьковая сортировка",
                "Сортировка выбором",
                "Сортировка вставками"
        });
        algorithmCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        algorithmCombo.setPreferredSize(new Dimension(200, 30));

        startButton = new JButton("СТАРТ");
        randomizeButton = new JButton("СЛУЧАЙНЫЙ");
        resetButton = new JButton("СБРОС");
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        startButton.setFont(buttonFont);
        randomizeButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);

        startButton.setBackground(new Color(76, 175, 80));
        startButton.setForeground(Color.BLACK);

        randomizeButton.setBackground(new Color(33, 150, 243));
        randomizeButton.setForeground(Color.BLACK);

        resetButton.setBackground(new Color(244, 67, 54));
        resetButton.setForeground(Color.BLACK);

        JLabel speedLabel = new JLabel("Скорость:");
        speedLabel.setForeground(Color.WHITE);
        speedLabel.setFont(new Font("Arial", Font.BOLD, 14));

        speedField = new JTextField("30", 4);
        speedField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel msLabel = new JLabel("мс");
        msLabel.setForeground(Color.WHITE);
        msLabel.setFont(new Font("Arial", Font.BOLD, 14));

        controlPanel.add(algorithmLabel);
        controlPanel.add(algorithmCombo);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(speedLabel);
        controlPanel.add(speedField);
        controlPanel.add(msLabel);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(startButton);
        controlPanel.add(randomizeButton);
        controlPanel.add(resetButton);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(38, 50, 56));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupFrame() {
        setTitle("Визуализация алгоритмов сортировки");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setResizable(true);
    }
}