import javax.swing.*;
import java.awt.*;



    public class MainFrame extends JFrame {
        private ImagePanel imagePanel;
        private ControlPanel controlPanel;

        public MainFrame() {
            setTitle("Image Processing App");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            setLocationRelativeTo(null);

            imagePanel = new ImagePanel();
            controlPanel = new ControlPanel(imagePanel);

            add(imagePanel, BorderLayout.CENTER);
            add(controlPanel, BorderLayout.SOUTH);
        }
    }
