package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Read-only dropdown appearance used for staff cells in assign-staff edit mode.
 */
public class StaffDropdownPanel extends JPanel {

    private static final int PANEL_WIDTH = 168;
    private static final int PANEL_HEIGHT = 30;

    public StaffDropdownPanel(String staffName, boolean notAssigned) {
        this(staffName, notAssigned, true);
    }

    public StaffDropdownPanel(String staffName, boolean notAssigned, boolean includeArrow) {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(AppColors.SURFACE);
        setBorder(new LineBorder(AppColors.GRID_LINE, 1, true));

        JPanel contentWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        contentWrap.setOpaque(false);
        contentWrap.setBorder(new EmptyBorder(2, 6, 2, includeArrow ? 4 : 6));

        if (notAssigned) {
            contentWrap.add(new NotAssignedBadgeLabel());
        } else {
            JLabel staffLabel = new JLabel(staffName, JLabel.CENTER);
            staffLabel.setFont(AppFonts.bodyBold());
            staffLabel.setForeground(AppColors.LABEL);
            contentWrap.add(staffLabel);
        }
        add(contentWrap, BorderLayout.CENTER);

        if (includeArrow) {
            JPanel arrowPanel = new JPanel(new BorderLayout());
            arrowPanel.setOpaque(false);
            arrowPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, AppColors.BORDER));
            arrowPanel.setPreferredSize(new Dimension(28, PANEL_HEIGHT));
            arrowPanel.add(StatusDropdownPanel.createDropdownArrow(), BorderLayout.CENTER);
            add(arrowPanel, BorderLayout.EAST);
        }

        Dimension size = new Dimension(includeArrow ? PANEL_WIDTH : PANEL_WIDTH - 28, PANEL_HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }
}
