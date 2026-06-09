package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class LogoPanel extends JPanel {

    public LogoPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(56, 56));
        setMaximumSize(new Dimension(56, 56));
        setMinimumSize(new Dimension(56, 56));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight());
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        g2.setColor(AppColors.PRIMARY);
        g2.fillRoundRect(x, y, size, size, 10, 10);

        int buildingW = size * 24 / 56;
        int buildingH = size * 28 / 56;
        int buildingX = x + (size - buildingW) / 2;
        int buildingY = y + size * 14 / 56;

        g2.setColor(AppColors.BUTTON_TEXT);
        g2.fillRect(buildingX, buildingY, buildingW, buildingH);

        int winW = size * 5 / 56;
        int winH = size * 5 / 56;
        int winGapX = size * 4 / 56;
        int winGapY = size * 3 / 56;
        int winStartX = buildingX + size * 4 / 56;
        int winStartY = buildingY + size * 4 / 56;
        g2.setColor(AppColors.PRIMARY);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 2; col++) {
                int wx = winStartX + col * (winW + winGapX);
                int wy = winStartY + row * (winH + winGapY);
                g2.fillRect(wx, wy, winW, winH);
            }
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
