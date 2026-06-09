package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class GradientBackgroundPanel extends JPanel {

    public GradientBackgroundPanel() {
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);

        GradientPaint topLeft = new GradientPaint(
                0, 0, Color.WHITE,
                (float) (w * 0.69), (float) (h * 0.91), AppColors.GRADIENT_LIGHT_CYAN);
        g2.setPaint(topLeft);
        g2.fillRect(0, 0, w, h);

        GradientPaint bottomRight = new GradientPaint(
                (float) (w * 0.19), (float) (h * 0.52), Color.WHITE,
                w, h, AppColors.GRADIENT_CYAN);
        g2.setPaint(bottomRight);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
        super.paintComponent(g);
    }
}
