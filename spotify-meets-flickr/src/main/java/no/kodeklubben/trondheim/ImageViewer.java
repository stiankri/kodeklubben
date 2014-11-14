package no.kodeklubben.trondheim;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewer extends JPanel {
    private final BufferedImage bufferedImage;

    ImageViewer(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0,this);
    }
}
