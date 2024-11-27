import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class HistogramPanel extends JPanel {
    private int histogramHeight = 200;
    private int barWidth = 50;
    private int barGap = 10;
    private JPanel barPanel;
    private JPanel labelPanel;
    private List<Bar> bars = new ArrayList<>();

    public HistogramPanel() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());

        barPanel = new JPanel(new GridLayout(1, 0, barGap, 0));
        barPanel.setPreferredSize(new Dimension(1000, histogramHeight)); // Set a large width for horizontal scrolling
        JScrollPane scrollPane = new JScrollPane(barPanel);
        add(scrollPane, BorderLayout.CENTER);

        labelPanel = new JPanel(new GridLayout(1, 0, barGap, 0));
        labelPanel.setBorder(new EmptyBorder(5, 10, 0, 10));
        add(labelPanel, BorderLayout.PAGE_END);
    }

    public void addHistogramColumn(String label, int value, Color color) {
        Bar bar = new Bar(label, value, color);
        bars.add(bar);
    }

    public void layoutHistogram() {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = 0;

        for (Bar bar : bars) {
            maxValue = Math.max(maxValue, bar.getValue());
        }

        for (Bar bar : bars) {
            // Create label for the bar value
            JLabel label = new JLabel(String.valueOf(bar.getValue()));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);

            // Calculate bar height and scale
            int barHeight = (bar.getValue() * histogramHeight) / maxValue;

            // Ensure bar height does not exceed panel size
            if (barHeight > histogramHeight) {
                barHeight = histogramHeight;
            }

            // Add a small margin between the bar and the label if necessary
            int labelMargin = 10;  // Adjust this value based on your needs
            Icon icon = new ColorIcon(bar.getColor(), barWidth, barHeight - labelMargin); // Reduce height slightly for label margin
            label.setIcon(icon);

            // Add bar icon and label to the panels
            barPanel.add(label);
            JLabel barLabel = new JLabel(bar.getLabel());
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add(barLabel);
        }

        revalidate();
        repaint();
    }

    private class Bar {
        private String label;
        private int value;
        private Color color;

        public Bar(String label, int value, Color color) {
            this.label = label;
            this.value = value;
            this.color = color;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }
    }

    private class ColorIcon implements Icon {
        private int shadow = 3;
        private Color color;
        private int width;
        private int height;

        public ColorIcon(Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int getIconWidth() {
            return width;
        }

        public int getIconHeight() {
            return height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, width - shadow, height);
            g.setColor(Color.GRAY);
            g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
        }
    }
}
