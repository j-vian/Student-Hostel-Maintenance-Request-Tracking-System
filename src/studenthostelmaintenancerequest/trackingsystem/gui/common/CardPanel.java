package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CardPanel extends JPanel {

    private static final int ARC = 0;
    private static final int SHADOW_OFFSET = 8;
    private static final int SHADOW_BLUR_LAYERS = 12;

    public CardPanel() {
        setOpaque(false);
        setBackground(AppColors.CARD);
        setBorder(new EmptyBorder(48, 56, 48, 56));
    }

    public void setCardSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;

        for (int i = SHADOW_BLUR_LAYERS; i >= 1; i--) {
            float alpha = 25f / 255f / i;
            g2.setColor(new Color(0f, 0f, 0f, alpha));
            int spread = i * 2;
            g2.fillRoundRect(
                    x - SHADOW_OFFSET - spread / 2,
                    y - SHADOW_OFFSET - spread / 2,
                    w + spread,
                    h + spread,
                    ARC,
                    ARC);
        }

        g2.setColor(AppColors.CARD);
        g2.fillRoundRect(x, y, w, h, ARC, ARC);

        g2.setColor(AppColors.BORDER);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRoundRect(x + 2, y + 2, w - 4, h - 4, ARC, ARC);

        g2.dispose();
        super.paintComponent(g);
    }
}
