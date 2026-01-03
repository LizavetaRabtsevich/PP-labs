package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Random;

public class Main extends JFrame {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 600;
    private static final int ARRAY_SIZE = 60;

    private final int[] array = new int[ARRAY_SIZE];
    private final int[] originalArray = new int[ARRAY_SIZE];
    private final SortPanel sortPanel = new SortPanel(array);
    private final JButton startButton = new JButton("Старт");
    private final JButton randomizeButton = new JButton("Случайный");
    private final JButton resetButton = new JButton("Сброс");

    private final JComboBox<String> algorithmCombo = new JComboBox<>(new String[]{
            "Пузырьковая сортировка", "Сортировка выбором", "Сортировка вставками"
    });

    private Timer timer;
    private int i = 0, j = 0, k = 0;
    private boolean sorting = false;
    private String currentAlgorithm = "Пузырьковая сортировка";

    private int minIndex = 0;
    private int currentIndex = 0;

    public Main() {
        super("Визуализация алгоритмов сортировки");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        generateRandomArray();
        System.arraycopy(array, 0, originalArray, 0, array.length);

        sortPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - 120));
        add(sortPanel, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 10));
        controls.add(new JLabel("Алгоритм:"));
        controls.add(algorithmCombo);
        controls.add(startButton);
        controls.add(randomizeButton);
        controls.add(resetButton);
        add(controls, BorderLayout.SOUTH);

        timer = new Timer(30, this::stepSort);

        startButton.addActionListener(this::onStart);
        randomizeButton.addActionListener(this::onRandomize);
        resetButton.addActionListener(this::onReset);
        algorithmCombo.addActionListener(e -> {
            if (!sorting) {
                currentAlgorithm = (String) algorithmCombo.getSelectedItem();
                resetArray();
            }
        });
    }

    private void onStart(ActionEvent e) {
        if (!sorting) {
            sorting = true;
            i = 0;
            j = 0;
            k = 0;
            minIndex = 0;
            currentIndex = 1;
            timer.start();
            startButton.setEnabled(false);
            randomizeButton.setEnabled(false);
            algorithmCombo.setEnabled(false);
        }
    }

    private void onRandomize(ActionEvent e) {
        if (!sorting) {
            generateRandomArray();
            System.arraycopy(array, 0, originalArray, 0, array.length);
            sortPanel.repaint();
        }
    }

    private void onReset(ActionEvent e) {
        timer.stop();
        sorting = false;
        i = 0;
        j = 0;
        k = 0;
        minIndex = 0;
        currentIndex = 1;
        resetArray();
        startButton.setEnabled(true);
        randomizeButton.setEnabled(true);
        algorithmCombo.setEnabled(true);
    }

    private void generateRandomArray() {
        Random rnd = new Random();
        for (int idx = 0; idx < array.length; idx++) {
            array[idx] = 10 + rnd.nextInt(sortPanel.getHeight() > 0 ? sortPanel.getHeight() - 50 : HEIGHT - 150);
        }
    }

    private void resetArray() {
        System.arraycopy(originalArray, 0, array, 0, array.length);
        sortPanel.setHighlight(-1, -1, -1);
        sortPanel.repaint();
    }

    private void stepSort(ActionEvent e) {
        switch (currentAlgorithm) {
            case "Пузырьковая сортировка":
                stepBubbleSort();
                break;
            case "Сортировка выбором":
                stepSelectionSort();
                break;
            case "Сортировка вставками":
                stepInsertionSort();
                break;
        }
    }

    private void stepBubbleSort() {
        if (i >= array.length - 1) {
            finishSorting();
            return;
        }

        sortPanel.setHighlight(j, j + 1, -1);

        if (array[j] > array[j + 1]) {
            int tmp = array[j];
            array[j] = array[j + 1];
            array[j + 1] = tmp;
        }

        j++;
        if (j >= array.length - 1 - i) {
            j = 0;
            i++;
        }

        sortPanel.repaint();
    }

    private void stepSelectionSort() {
        if (i >= array.length - 1) {
            finishSorting();
            return;
        }
        if (j < array.length) {
            sortPanel.setHighlight(i, j, minIndex);

            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
            j++;
        } else {
            if (minIndex != i) {
                int tmp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = tmp;
            }
            i++;
            j = i + 1;
            minIndex = i;
        }
        sortPanel.repaint();
    }

    private void stepInsertionSort() {
        if (i >= array.length) {
            finishSorting();
            return;
        }
        if (i == 0) {
            i++;
            currentIndex = i;
            k = i - 1;
        }
        sortPanel.setHighlight(currentIndex, k, -1);
        if (k >= 0 && array[currentIndex] < array[k]) {
            int tmp = array[k];
            array[k] = array[currentIndex];
            array[currentIndex] = tmp;
            currentIndex = k;
            k--;
        } else {
            i++;
            currentIndex = i;
            k = i - 1;
        }

        sortPanel.repaint();
    }

    private void finishSorting() {
        timer.stop();
        sorting = false;
        startButton.setEnabled(true);
        randomizeButton.setEnabled(true);
        algorithmCombo.setEnabled(true);
        sortPanel.setHighlight(-1, -1, -1);
        sortPanel.repaint();
    }

    private static class SortPanel extends JPanel {
        private final int[] array;
        private int highlightA = -1;
        private int highlightB = -1;
        private int highlightC = -1;

        public SortPanel(int[] array) {
            this.array = array;
            setBackground(new Color(0x263238));
        }

        public void setHighlight(int a, int b, int c) {
            this.highlightA = a;
            this.highlightB = b;
            this.highlightC = c;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (array.length == 0) return;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            int padding = 20;
            int barSpace = width - 2 * padding;
            int barWidth = Math.max(2, barSpace / array.length);
            int gap = Math.max(1, (barSpace - barWidth * array.length) / Math.max(1, array.length - 1));
            int x = padding;

            int maxValue = Arrays.stream(array).max().orElse(1);
            double scale = maxValue > 0 ? (height - 2.0 * padding) / maxValue : 1.0;

            for (int i = 0; i < array.length; i++) {
                int barHeight = (int) Math.round(array[i] * scale);
                int y = height - padding - barHeight;

                Color color;
                if (i == highlightA) {
                    color = new Color(0xFF7043); // Оранжевый - текущий сравниваемый
                } else if (i == highlightB) {
                    color = new Color(0x4FC3F7); // Голубой - второй сравниваемый
                } else if (i == highlightC) {
                    color = new Color(0x81C784); // Зеленый - минимальный (для выбора)
                } else {
                    color = new Color(0x78909C); // Серый - обычный
                }

                g2.setColor(color);
                g2.fillRect(x, y, barWidth, barHeight);

                g2.setColor(new Color(0x37474F));
                g2.drawRect(x, y, barWidth, barHeight);

                if (array.length <= 30) {
                    g2.setColor(Color.WHITE);
                    g2.setFont(getFont().deriveFont(Font.PLAIN, 10f));
                    String text = String.valueOf(array[i]);
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(text);
                    int textX = x + (barWidth - textWidth) / 2;
                    int textY = y - 5;
                    g2.drawString(text, textX, textY);
                }

                x += barWidth + gap;
            }

            g2.setColor(Color.WHITE);
            g2.setFont(getFont().deriveFont(Font.BOLD, 18f));
            String title = "Визуализация алгоритмов сортировки";
            FontMetrics fm = g2.getFontMetrics();
            int titleX = (width - fm.stringWidth(title)) / 2;
            g2.drawString(title, titleX, padding + 20);

            g2.setFont(getFont().deriveFont(Font.PLAIN, 14f));
            String info = String.format("Количество элементов: %d | Размер массива: %d",
                    array.length, array.length);
            int infoX = (width - fm.stringWidth(info)) / 2;
            g2.drawString(info, infoX, padding + 45);

            int legendY = height - 25;
            g2.setFont(getFont().deriveFont(Font.PLAIN, 12f));

            g2.setColor(new Color(0xFF7043));
            g2.fillRect(width - 300, legendY - 15, 15, 10);
            g2.setColor(Color.WHITE);
            g2.drawString("Сравниваемый 1", width - 280, legendY - 5);

            g2.setColor(new Color(0x4FC3F7));
            g2.fillRect(width - 150, legendY - 15, 15, 10);
            g2.setColor(Color.WHITE);
            g2.drawString("Сравниваемый 2", width - 130, legendY - 5);

            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Main app = new Main();
            app.setVisible(true);
        });
    }
}