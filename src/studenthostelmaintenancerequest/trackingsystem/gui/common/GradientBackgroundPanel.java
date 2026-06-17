package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class GradientBackgroundPanel extends JPanel {

    // construct frame and initialize UI
    public GradientBackgroundPanel() {
        setOpaque(true);
        setBackground(AppColors.GRADIENT_LIGHT_CYAN);
    }

    @Override
    // process business logic
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        float radius = Math.max(w, h) * 0.72f;
        RadialGradientPaint gradient = new RadialGradientPaint(
                new Point(w / 2, h / 2),
                radius,
                new float[]{0f, 0.42f, 1f},
                new Color[]{
                    Color.WHITE,
                    AppColors.GRADIENT_LIGHT_CYAN,
                    AppColors.GRADIENT_EDGE
                });
        g2.setPaint(gradient);
        g2.fillRect(0, 0, w, h);
        g2.dispose();
    }
}
