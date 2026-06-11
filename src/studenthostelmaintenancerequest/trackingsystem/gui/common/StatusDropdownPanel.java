package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Read-only dropdown appearance used for status cells in manage-request edit mode.
 */
public class StatusDropdownPanel extends JPanel {

    private static final int PANEL_WIDTH = 148;
    private static final int PANEL_HEIGHT = 30;

    public StatusDropdownPanel(String status) {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(AppColors.SURFACE);
        setBorder(new LineBorder(AppColors.GRID_LINE, 1, true));

        JPanel badgeWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        badgeWrap.setOpaque(false);
        badgeWrap.setBorder(new EmptyBorder(2, 6, 2, 4));
        badgeWrap.add(new StatusBadgeLabel(status));
        add(badgeWrap, BorderLayout.CENTER);

        JPanel arrowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        arrowPanel.setOpaque(false);
        arrowPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, AppColors.BORDER));
        arrowPanel.setPreferredSize(new Dimension(28, PANEL_HEIGHT));

        JLabel arrow = new JLabel("\u25BE");
        arrow.setFont(AppFonts.body());
        arrow.setForeground(AppColors.LABEL);
        arrowPanel.add(arrow);
        add(arrowPanel, BorderLayout.EAST);

        Dimension size = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }
}
