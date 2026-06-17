package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class ManagerNavButton extends JButton {

    // constants used by this class
    private static final int HORIZONTAL_INSET = 12;
    private static final int VERTICAL_GAP = 4;

    private boolean active;

    /** No-arg constructor required by the NetBeans Form Editor at design time. */
    // construct frame and initialize UI
    public ManagerNavButton() {
        this("Menu");
    }

    // construct frame and initialize UI
    public ManagerNavButton(String text) {
        super(text);
        setFont(AppFonts.bodyBold());
        setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        setAlignmentX(javax.swing.JComponent.LEFT_ALIGNMENT);
    }

    // update object state
    public void setActive(boolean active) {
        this.active = active;
        setForeground(active ? AppColors.PRIMARY : AppColors.LABEL);
        repaint();
    }

    public boolean isActive() {
        return active;
    }

    public static int verticalGap() {
        return VERTICAL_GAP;
    }

    public static int horizontalInset() {
        return HORIZONTAL_INSET;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (active) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(AppColors.NAV_ACTIVE_BG);
            int arc = getHeight();
            g2.fillRoundRect(HORIZONTAL_INSET, 0, getWidth() - (HORIZONTAL_INSET * 2), getHeight(), arc, arc);
            g2.dispose();
        }
        super.paintComponent(g);
    }
}
