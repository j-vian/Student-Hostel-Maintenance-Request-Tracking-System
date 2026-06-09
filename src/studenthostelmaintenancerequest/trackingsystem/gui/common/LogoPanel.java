package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class LogoPanel extends JPanel {

    public LogoPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(88, 88));
        setMaximumSize(new Dimension(88, 88));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 8;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        g2.setColor(AppColors.PRIMARY);
        g2.fillRoundRect(x, y, size, size, 12, 12);

        g2.setColor(AppColors.BUTTON_TEXT);
        int pad = size / 5;
        int gridW = (size - pad * 2) / 3;
        int gridH = (size - pad * 2) / 3;
        int startX = x + pad;
        int startY = y + pad;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int wx = startX + col * gridW + 2;
                int wy = startY + row * gridH + 2;
                g2.fillRect(wx, wy, gridW - 4, gridH - 4);
            }
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
