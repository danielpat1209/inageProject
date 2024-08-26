import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;



    public class ControlPanel extends JPanel {
        private ImagePanel imagePanel;
        private BufferedImage originalImage;
        private List<Integer> activeFilters;
        private JTextField contrastField;

        public ControlPanel(ImagePanel imagePanel) {
            this.imagePanel = imagePanel;
            this.activeFilters = new ArrayList<>();

            setLayout(new BorderLayout());


            JPanel topPanel = new JPanel(new GridLayout(2, 1));


            JButton openButton = new JButton("Open Image");
            openButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(ControlPanel.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        originalImage = ImageLoader.loadImage(fileChooser.getSelectedFile().getPath());
                        imagePanel.setImage(originalImage);
                        activeFilters.clear(); // Clear active filters when a new image is loaded
                    }
                }
            });
            topPanel.add(openButton);

            // Clear Points button
            JButton clearPointsButton = new JButton("Clear Points");
            clearPointsButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    imagePanel.clearPoints();
                    imagePanel.setImage(originalImage); // Reset to the original image
                    activeFilters.clear(); // Clear active filters when points are cleared
                }
            });
            topPanel.add(clearPointsButton);

            add(topPanel, BorderLayout.NORTH);

            // Create a panel for the filter buttons
            JPanel buttonPanel = new JPanel(new GridLayout(5, 2)); // 5 rows, 2 columns

            String[] filters = {"Red Filter", "Grayscale", "Black and White", "Mirror", "Noise", "Contrast", "Inverted Colors", "Pixelated", "Sepia", "Vintage", "Lighter", "darker"};
            for (int i = 0; i < filters.length; i++) {
                JButton button = new JButton(filters[i]);
                int filterType = i + 1; // filterType 1 for Red Filter, 2 for Grayscale, etc.
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (originalImage != null) {
                            toggleFilter(filterType);
                        }
                    }
                });
                buttonPanel.add(button);
            }

            add(buttonPanel, BorderLayout.CENTER);

            // Contrast control
            JPanel contrastPanel = new JPanel();
            JLabel contrastLabel = new JLabel("Contrast Factor:");
            contrastPanel.add(contrastLabel);
            contrastField = new JTextField("1.0", 5);
            contrastPanel.add(contrastField);
            add(contrastPanel, BorderLayout.SOUTH);
        }

        private void toggleFilter(int filterType) {
            // Toggle the filter state
            if (activeFilters.contains(filterType)) {
                activeFilters.remove(Integer.valueOf(filterType));
            } else {
                activeFilters.add(filterType);
            }
            applyFilters();
        }

        private void applyFilters() {
            BufferedImage filteredImage = copyImage(originalImage);
            java.util.List<Point> points = imagePanel.getPoints();

            if (points.size() == 4) {
                Polygon polygon = new Polygon();
                for (Point point : points) {
                    polygon.addPoint(point.x, point.y);
                }
                for (int filterType : activeFilters) {
                    if (filterType == 6) { // Contrast needs an extra parameter
                        double contrastFactor = Double.parseDouble(contrastField.getText());
                        filteredImage = applyFilterToPolygon(filteredImage, filterType, polygon, contrastFactor);
                    } else {
                        filteredImage = applyFilterToPolygon(filteredImage, filterType, polygon);
                    }
                }
            } else {
                for (int filterType : activeFilters) {
                    if (filterType == 6) { // Contrast needs an extra parameter
                        double contrastFactor = Double.parseDouble(contrastField.getText());
                        filteredImage = Filters.applyFilter(filteredImage, filterType, contrastFactor);
                    } else {
                        filteredImage = Filters.applyFilter(filteredImage, filterType);
                    }
                }
            }

            imagePanel.setImage(filteredImage);
        }

        private BufferedImage applyFilterToPolygon(BufferedImage image, int filterType, Polygon polygon) {
            return applyFilterToPolygon(image, filterType, polygon, 1.0);
        }

        private BufferedImage applyFilterToPolygon(BufferedImage image, int filterType, Polygon polygon, double contrastFactor) {
            BufferedImage result = copyImage(image);
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (polygon.contains(x, y)) {
                        Color color = new Color(image.getRGB(x, y));
                        switch (filterType) {
                            case 1:
                                color = new Color(Math.min(color.getRed() + 20, 255), color.getGreen(), color.getBlue());
                                break;
                            case 2:
                                int gray = (int) (0.3 * color.getRed() + 0.6 * color.getGreen() + 0.1 * color.getBlue());
                                color = new Color(gray, gray, gray);
                                break;
                            case 3:
                                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                                color = avg > 128 ? Color.WHITE : Color.BLACK;
                                break;
                            case 4:
                                int mirroredX = image.getWidth() - x - 1;
                                color = new Color(image.getRGB(mirroredX, y));
                                break;
                            case 5:
                                int noise = (int) (Math.random() * 50) - 25;
                                color = new Color(
                                        Math.min(Math.max(color.getRed() + noise, 0), 255),
                                        Math.min(Math.max(color.getGreen() + noise, 0), 255),
                                        Math.min(Math.max(color.getBlue() + noise, 0), 255)
                                );
                                break;
                            case 6:
                                color = new Color(
                                        Math.min(Math.max((int) (contrastFactor * (color.getRed() - 128) + 128), 0), 255),
                                        Math.min(Math.max((int) (contrastFactor * (color.getGreen() - 128) + 128), 0), 255),
                                        Math.min(Math.max((int) (contrastFactor * (color.getBlue() - 128) + 128), 0), 255)
                                );
                                break;
                            case 7:
                                color = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
                                break;
                            case 8:
                                // Apply pixelated logic here
                                break;
                            case 9:
                                int tr = (int) (0.393 * color.getRed() + 0.769 * color.getGreen() + 0.189 * color.getBlue());
                                int tg = (int) (0.349 * color.getRed() + 0.686 * color.getGreen() + 0.168 * color.getBlue());
                                int tb = (int) (0.272 * color.getRed() + 0.534 * color.getGreen() + 0.131 * color.getBlue());
                                color = new Color(Math.min(tr, 255), Math.min(tg, 255), Math.min(tb, 255));
                                break;
                            case 10:
                                int vintageNoise = (int) (Math.random() * 50) - 25;
                                color = new Color(
                                        Math.min(Math.max(color.getRed() + vintageNoise, 0), 255),
                                        Math.min(Math.max(color.getGreen() + vintageNoise, 0), 255),
                                        Math.min(Math.max(color.getBlue() + vintageNoise, 0), 255)
                                );
                                color = new Color(
                                        Math.min(Math.max((int) (0.393 * color.getRed() + 0.769 * color.getGreen() + 0.189 * color.getBlue()), 0), 255),
                                        Math.min(Math.max((int) (0.349 * color.getRed() + 0.686 * color.getGreen() + 0.168 * color.getBlue()), 0), 255),
                                        Math.min(Math.max((int) (0.272 * color.getRed() + 0.534 * color.getGreen() + 0.131 * color.getBlue()), 0), 255)
                                );
                                break;
                        }
                        result.setRGB(x, y, color.getRGB());
                    }
                }
            }
            return result;
        }

        private BufferedImage copyImage(BufferedImage source) {
            BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
            Graphics g = copy.getGraphics();
            g.drawImage(source, 0, 0, null);
            g.dispose();
            return copy;
        }
    }




