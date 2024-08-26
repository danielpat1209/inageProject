
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private List<Point> points = new ArrayList<>();

    public ImagePanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (points.size() < 4) {
                    points.add(e.getPoint());
                    repaint();
                }
            }
        });
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public BufferedImage getCurrentImage() {
        return image;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void clearPoints() {
        points.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;
            g.drawImage(image, x, y, this);

            g.setColor(Color.RED);
            for (Point point : points) {
                g.fillOval(point.x - 5, point.y - 5, 10, 10);
            }

            if (points.size() == 4) {
                g.drawLine(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);
                g.drawLine(points.get(1).x, points.get(1).y, points.get(2).x, points.get(2).y);
                g.drawLine(points.get(2).x, points.get(2).y, points.get(3).x, points.get(3).y);
                g.drawLine(points.get(3).x, points.get(3).y, points.get(0).x, points.get(0).y);
            }
        }
    }
}
