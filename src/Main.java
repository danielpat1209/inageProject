import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/// participants: Daniel Patachov, Alex

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();

    }

    public Main() {
        File file = new File("C:\\Users\\danie\\OneDrive\\תמונות\\LAMBORGHINIHuracan1111_c.jpg");
        if (file.exists()) {
            try {

                BufferedImage bufferedImage = ImageIO.read(file);
                this.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());

                this.setLocationRelativeTo(null);
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                this.setVisible(true);


            } catch (IOException e) {
                throw new RuntimeException();
            }

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                }
            });
        }
    }
}
