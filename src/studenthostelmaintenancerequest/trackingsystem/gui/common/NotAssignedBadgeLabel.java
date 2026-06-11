package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class NotAssignedBadgeLabel extends JLabel {

    public NotAssignedBadgeLabel() {
        super("NOT ASSIGNED");
        setFont(AppFonts.statusBadge());
        setForeground(AppColors.MUTED);
        setBackground(AppColors.STAT_TOTAL_BODY);
        setOpaque(false);
        setBorder(new EmptyBorder(4, 12, 4, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        int arc = getHeight();
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();
        super.paintComponent(g);
    }
}
