import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FilenameFilter;


public class Filters {
        public static BufferedImage applyFilter(BufferedImage image, int filterType) {
            return applyFilter(image, filterType, 1.0); // Default contrast factor
        }

        public static BufferedImage applyFilter(BufferedImage image, int filterType, double contrastFactor) {
            switch (filterType) {
                case 1:
                    return applyRedFilter(image, 20);
                case 2:
                    return applyGrayscale(image);
                case 3:
                    return applyBlackAndWhite(image);
                case 4:
                    return applyMirror(image);
                case 5:
                    return applyNoise(image);
                case 6:
                    return applyContrast(image,10);
                case 7:
                    return applyInvertedColors(image);
                case 8:
                    return applyPixelated(image);
                case 9:
                    return applySepia(image);
                case 10:
                    return applyVintage(image);
                case 11:
                    return applyBrighten(image,20);
                case 12:
                    return applyDarkerFilter(image);
                default:
                    throw new IllegalArgumentException("Invalid filter type: " + filterType);
            }
        }

        public static BufferedImage applyRedFilter(BufferedImage image, int redIncrease) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int rgb = image.getRGB(i, j);
                    Color currentColor = new Color(rgb);
                    int newRed = currentColor.getRed() + redIncrease;
                    if (newRed > 255) {
                        newRed = 255;
                    }
                    Color newColor = new Color(newRed, currentColor.getGreen(), currentColor.getBlue());
                    image.setRGB(i, j, newColor.getRGB());
                }
            }
            return image;
        }

        public static BufferedImage applyGrayscale(BufferedImage image) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int rgb = image.getRGB(i, j);
                    Color color = new Color(rgb);
                    int gray = (int) (0.3 * color.getRed() + 0.6 * color.getGreen() + 0.1 * color.getBlue());
                    Color newColor = new Color(gray, gray, gray);
                    image.setRGB(i, j, newColor.getRGB());
                }
            }
            return image;
        }

        public static BufferedImage applyBlackAndWhite(BufferedImage image) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int rgb = image.getRGB(i, j);
                    Color color = new Color(rgb);
                    int average = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    Color newColor = average > 128 ? Color.WHITE : Color.BLACK;
                    image.setRGB(i, j, newColor.getRGB());
                }
            }
            return image;
        }

        public static BufferedImage applyMirror(BufferedImage image) {
            int width = image.getWidth();
            int height = image.getHeight();
            BufferedImage result = new BufferedImage(width, height, image.getType());
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int mirroredX = width - x - 1;
                    int rgb = image.getRGB(x, y);
                    result.setRGB(mirroredX, y, rgb);
                }
            }
            return result;
        }

        public static BufferedImage applyNoise(BufferedImage image) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int rgb = image.getRGB(i, j);
                    Color currentColor = new Color(rgb);
                    int noise = (int) (Math.random() * 50) - 25; // Random noise (between -25 and 25)
                    int red = Math.min(Math.max(currentColor.getRed() + noise, 0), 255);
                    int green = Math.min(Math.max(currentColor.getGreen() + noise, 0), 255);
                    int blue = Math.min(Math.max(currentColor.getBlue() + noise, 0), 255);
                    Color newColor = new Color(red, green, blue);
                    image.setRGB(i, j, newColor.getRGB());
                }
            }
            return image;
        }

        public static BufferedImage applyContrast(BufferedImage image, double contrastFactor) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int rgb = image.getRGB(i, j);
                    Color color = new Color(rgb);
                    int red = Math.min(Math.max((int) (contrastFactor * (color.getRed() - 128) + 128), 0), 255);
                    int green = Math.min(Math.max((int) (contrastFactor * (color.getGreen() - 128) + 128), 0), 255);
                    int blue = Math.min(Math.max((int) (contrastFactor * (color.getBlue() - 128) + 128), 0), 255);
                    Color newColor = new Color(red, green, blue);
                    image.setRGB(i, j, newColor.getRGB());
                }
            }
            return image;
        }

        public static BufferedImage applyInvertedColors(BufferedImage image) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int rgb = image.getRGB(i, j);
                    Color color = new Color(rgb);
                    Color newColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
                    image.setRGB(i, j, newColor.getRGB());
                }
            }
            return image;
        }

        public static BufferedImage applyPixelated(BufferedImage image) {
            int pixelSize = 10; // Fixed pixel size
            for (int y = 0; y < image.getHeight(); y += pixelSize) {
                for (int x = 0; x < image.getWidth(); x += pixelSize) {
                    int rgb = image.getRGB(x, y);
                    for (int yd = 0; yd < pixelSize; yd++) {
                        for (int xd = 0; xd < pixelSize; xd++) {
                            if (x + xd < image.getWidth() && y + yd < image.getHeight()) {
                                image.setRGB(x + xd, y + yd, rgb);
                            }
                        }
                    }
                }
            }
            return image;
        }

        public static BufferedImage applySepia(BufferedImage image) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int rgb = image.getRGB(i, j);
                    Color color = new Color(rgb);
                    int tr = (int) (0.393 * color.getRed() + 0.769 * color.getGreen() + 0.189 * color.getBlue());
                    int tg = (int) (0.349 * color.getRed() + 0.686 * color.getGreen() + 0.168 * color.getBlue());
                    int tb = (int) (0.272 * color.getRed() + 0.534 * color.getGreen() + 0.131 * color.getBlue());
                    Color newColor = new Color(Math.min(tr, 255), Math.min(tg, 255), Math.min(tb, 255));
                    image.setRGB(i, j, newColor.getRGB());
                }
            }
            return image;
        }

        public static BufferedImage applyVintage(BufferedImage image) {
            BufferedImage sepiaImage = applySepia(image);
            return applyNoise(sepiaImage);
        }
        public static BufferedImage applyBrighten(BufferedImage image,int percentage){
            int r=0,g=0,b=0,rgb=0,p=0;
            int amount=(int) (percentage*255/100);
            BufferedImage newImage=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
            for (int x=0;x<image.getWidth();x+=1){
                for (int y=0;y<image.getHeight();y+=1){
                    rgb=image.getRGB(x,y);
                    r=((rgb >> 16)& 0XFF)+amount;
                    g=((rgb >> 8)& 0XFF)+amount;
                    b=(rgb & 0XFF)+amount;
                    if (r>255) r=255;
                    if (g>255) g=255;
                    if (b>255) b=255;
                    p=(255<<24)| (r<<16) | (g<<8) | b;
                    newImage.setRGB(x,y,p);

                }

            }
            return newImage;

        }
    public static BufferedImage applyDarkerFilter(BufferedImage image) {
        BufferedImage darkImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Apply dark black filter
        for (int x = 0; x < darkImage.getWidth(); x++) {
            for (int y = 0; y < darkImage.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xff;
                int red = (rgba >> 16) & 0xff;
                int green = (rgba >> 8) & 0xff;
                int blue = rgba & 0xff;
                red = Math.max(0, red - 50);
                green = Math.max(0, green - 50);
                blue = Math.max(0, blue - 50);

                int newRGBA = (alpha << 24) | (red << 16) | (green << 8) | blue;
                darkImage.setRGB(x, y, newRGBA);
            }
        }

        return darkImage;
    }
}








