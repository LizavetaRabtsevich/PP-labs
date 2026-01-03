package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Main {
    private static final int ARRAY_SIZE = 60;
    private final int[] array = new int[ARRAY_SIZE];
    private final int[] originalArray = new int[ARRAY_SIZE];
    private SortingFrame frame;
    private SortPanel sortPanel;
    private Timer timer;
    private int i = 0, j = 0, k = 0;
    private boolean sorting = false;
    private String currentAlgorithm = "Пузырьковая сортировка";
    private int minIndex = 0;
    private int currentIndex = 0;
    private int comparisons = 0;
    private int swaps = 0;

    public Main() {
        frame = new SortingFrame();
        sortPanel = new SortPanel(array);
        frame.getMainPanel().add(sortPanel);
        generateRandomArray();
        System.arraycopy(array, 0, originalArray, 0, array.length);
        int speed = getSpeedFromField();
        timer = new Timer(speed, this::stepSort);
        setupListeners();
        frame.setVisible(true);
    }

    private void setupListeners() {
        frame.getStartButton().addActionListener(e -> onStart());
        frame.getRandomizeButton().addActionListener(e -> onRandomize());
        frame.getResetButton().addActionListener(e -> onReset());
        frame.getAlgorithmCombo().addActionListener(e -> {
            if (!sorting) {
                currentAlgorithm = (String) frame.getAlgorithmCombo().getSelectedItem();
                onReset();
            }
        });
        frame.getSpeedField().addActionListener(e -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
                timer.setDelay(getSpeedFromField());
                timer.start();
            } else {
                timer.setDelay(getSpeedFromField());
            }
        });
    }

    private int getSpeedFromField() {
        try {
            int speed = Integer.parseInt(frame.getSpeedField().getText());
            return Math.max(1, Math.min(speed, 1000));
        } catch (NumberFormatException e) {
            return 30;
        }
    }

    private void onStart() {
        if (!sorting) {
            sorting = true;
            i = 0; j = 0; k = 0;
            minIndex = 0; currentIndex = 1;
            comparisons = 0; swaps = 0;

            timer.setDelay(getSpeedFromField());
            timer.start();

            setControlsEnabled(false);
        }
    }

    private void onRandomize() {
        if (!sorting) {
            generateRandomArray();
            System.arraycopy(array, 0, originalArray, 0, array.length);
            sortPanel.setArray(array);
            sortPanel.setHighlight(-1, -1, -1);
        }
    }

    private void onReset() {
        timer.stop();
        sorting = false;
        i = 0; j = 0; k = 0;
        minIndex = 0; currentIndex = 1;

        System.arraycopy(originalArray, 0, array, 0, array.length);
        sortPanel.setArray(array);
        sortPanel.setHighlight(-1, -1, -1);

        setControlsEnabled(true);
    }

    private void generateRandomArray() {
        Random rnd = new Random();
        for (int idx = 0; idx < array.length; idx++) {
            array[idx] = 10 + rnd.nextInt(400);
        }
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
        comparisons++;

        if (array[j] > array[j + 1]) {
            int tmp = array[j];
            array[j] = array[j + 1];
            array[j + 1] = tmp;
            swaps++;
        }

        j++;
        if (j >= array.length - 1 - i) {
            j = 0;
            i++;
        }

        sortPanel.setArray(array);
    }

    private void stepSelectionSort() {
        if (i >= array.length - 1) {
            finishSorting();
            return;
        }

        if (j < array.length) {
            sortPanel.setHighlight(i, j, minIndex);
            comparisons++;

            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
            j++;
        } else {
            if (minIndex != i) {
                int tmp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = tmp;
                swaps++;
            }

            i++;
            j = i + 1;
            minIndex = i;
        }

        sortPanel.setArray(array);
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
        comparisons++;

        if (k >= 0 && array[currentIndex] < array[k]) {
            int tmp = array[k];
            array[k] = array[currentIndex];
            array[currentIndex] = tmp;
            swaps++;

            currentIndex = k;
            k--;
        } else {
            i++;
            currentIndex = i;
            k = i - 1;
        }

        sortPanel.setArray(array);
    }

    private void finishSorting() {
        timer.stop();
        sorting = false;
        sortPanel.setHighlight(-1, -1, -1);
        setControlsEnabled(true);

        JOptionPane.showMessageDialog(frame,
                "Сортировка завершена!\n",
                "Готово",    //comparison, swaps
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void setControlsEnabled(boolean enabled) {
        frame.getStartButton().setEnabled(enabled);
        frame.getRandomizeButton().setEnabled(enabled);
        frame.getAlgorithmCombo().setEnabled(enabled);
        frame.getSpeedField().setEnabled(enabled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Main();
        });
    }
}