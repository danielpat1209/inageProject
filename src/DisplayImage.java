
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayImage extends JPanel{
    private BufferedImage image;

    public DisplayImage(BufferedImage image){
        this.image=image;

    }
    protected void paintComponent(Graphics g){
        super.paint(g);
        g.drawImage(this.image,0,0,this.image.getWidth(),this.image.getHeight(),null);
    }
}
