package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SortPanel extends JPanel {
    private int[] array;
    private int highlightA = -1;
    private int highlightB = -1;
    private int highlightC = -1;

    public SortPanel(int[] array) {
        this.array = array;
        setBackground(new Color(0x263238));
        setPreferredSize(new Dimension(1000, 450));
    }

    public void setArray(int[] array) {
        this.array = array;
        repaint();
    }

    public void setHighlight(int a, int b, int c) {
        this.highlightA = a;
        this.highlightB = b;
        this.highlightC = c;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (array == null || array.length == 0) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int padding = 20;
        int barSpace = width - 2 * padding;

        int barWidth = Math.max(2, barSpace / array.length);
        int gap = Math.max(1, (barSpace - barWidth * array.length) /
                Math.max(1, array.length - 1));

        int maxValue = Arrays.stream(array).max().orElse(1);
        double scale = maxValue > 0 ? (height - 2.0 * padding) / maxValue : 1.0;

        int x = padding;
        for (int i = 0; i < array.length; i++) {
            int barHeight = (int) Math.round(array[i] * scale);
            int y = height - padding - barHeight;

            Color color;
            if (i == highlightA) {
                color = new Color(0xFF7043);
            } else if (i == highlightB) {
                color = new Color(0x4FC3F7);
            } else if (i == highlightC) {
                color = new Color(0x81C784);
            } else {
                color = new Color(0x78909C);
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
        g2.setFont(getFont().deriveFont(Font.BOLD, 20f));
        String title = "Визуализация алгоритмов сортировки";
        FontMetrics fm = g2.getFontMetrics();
        int titleX = (width - fm.stringWidth(title)) / 2;
        g2.drawString(title, titleX, padding + 25);

        g2.setFont(getFont().deriveFont(Font.PLAIN, 14f));
        String info = String.format("Элементов: %d | Сравнений: %d",
                array.length, getComparisonCount());
        int infoX = (width - fm.stringWidth(info)) / 2;
        g2.drawString(info, infoX, padding + 50);

        drawLegend(g2, width, height);
    }

    private int getComparisonCount() {
        return (highlightA >= 0 && highlightB >= 0) ? 1 : 0;
    }

    private void drawLegend(Graphics2D g2, int width, int height) {
        int legendY = height - 30;
        g2.setFont(getFont().deriveFont(Font.PLAIN, 12f));

        g2.setColor(new Color(0xFF7043));
        g2.fillRect(width - 320, legendY - 12, 15, 10);
        g2.setColor(Color.WHITE);
        g2.drawString("Сравниваемый 1", width - 300, legendY - 2);

        g2.setColor(new Color(0x4FC3F7));
        g2.fillRect(width - 150, legendY - 12, 15, 10);
        g2.setColor(Color.WHITE);
        g2.drawString("Сравниваемый 2", width - 130, legendY - 2);

        g2.setColor(new Color(0x81C784));
        g2.fillRect(width - 320, legendY + 10, 15, 10);
        g2.setColor(Color.WHITE);
        g2.drawString("Минимальный", width - 300, legendY + 20);
    }
}