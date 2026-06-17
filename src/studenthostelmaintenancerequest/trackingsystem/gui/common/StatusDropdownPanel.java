package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Read-only dropdown appearance used for status cells in manage-request edit mode.
 */
public class StatusDropdownPanel extends JPanel {

    // constants used by this class
    private static final int PANEL_WIDTH = 148;
    private static final int PANEL_HEIGHT = 30;

    // construct frame and initialize UI
    public StatusDropdownPanel(String status) {
        this(status, true);
    }

    /**
     * @param includeArrow when false, only the badge area is shown (for use inside JComboBox
     *                     where the LAF draws the arrow button separately)
     */
    // construct frame and initialize UI
    public StatusDropdownPanel(String status, boolean includeArrow) {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(AppColors.SURFACE);
        setBorder(new LineBorder(AppColors.GRID_LINE, 1, true));

        JPanel badgeWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        badgeWrap.setOpaque(false);
        badgeWrap.setBorder(new EmptyBorder(2, 6, 2, includeArrow ? 4 : 6));
        badgeWrap.add(new StatusBadgeLabel(status));
        add(badgeWrap, BorderLayout.CENTER);

        if (includeArrow) {
            JPanel arrowPanel = new JPanel(new BorderLayout());
            arrowPanel.setOpaque(false);
            arrowPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, AppColors.BORDER));
            arrowPanel.setPreferredSize(new Dimension(28, PANEL_HEIGHT));
            arrowPanel.add(createDropdownArrow(), BorderLayout.CENTER);
            add(arrowPanel, BorderLayout.EAST);
        }

        Dimension size = new Dimension(includeArrow ? PANEL_WIDTH : PANEL_WIDTH - 28, PANEL_HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    // create and return configured object
    static JComponent createDropdownArrow() {
        JComponent arrow = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.LABEL);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2 + 1;
                int[] xs = {cx - 5, cx + 5, cx};
                int[] ys = {cy - 3, cy - 3, cy + 3};
                g2.fillPolygon(xs, ys, 3);
                g2.dispose();
            }
        };
        Dimension arrowSize = new Dimension(28, PANEL_HEIGHT);
        arrow.setPreferredSize(arrowSize);
        arrow.setMinimumSize(arrowSize);
        arrow.setOpaque(false);
        return arrow;
    }
}
