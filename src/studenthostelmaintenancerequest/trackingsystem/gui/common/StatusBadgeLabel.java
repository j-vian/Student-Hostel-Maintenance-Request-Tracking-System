package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class StatusBadgeLabel extends JLabel {

    /** No-arg constructor required by the NetBeans Form Editor at design time. */
    // construct frame and initialize UI
    public StatusBadgeLabel() {
        this("SUBMITTED");
    }

    // construct frame and initialize UI
    public StatusBadgeLabel(String status) {
        super(formatStatus(status));
        applyStatusStyle(status);
    }

    // process business logic
    public static String formatStatus(String status) {
        if (status == null) {
            return "";
        }
        return status.trim().replace('_', ' ').toUpperCase();
    }

    public static void applyStatusStyle(JLabel label, String status) {
        label.setText(formatStatus(status));
        label.setFont(AppFonts.statusBadge());
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(4, 12, 4, 12));

        String normalized = formatStatus(status);
        switch (normalized) {
            case "IN PROGRESS" -> {
                label.setForeground(AppColors.WARNING);
                label.setBackground(AppColors.STATUS_IN_PROGRESS_BG);
            }
            case "SUBMITTED" -> {
                label.setForeground(AppColors.PRIMARY);
                label.setBackground(AppColors.STATUS_SUBMITTED_BG);
            }
            case "COMPLETED" -> {
                label.setForeground(AppColors.SUCCESS);
                label.setBackground(AppColors.STATUS_COMPLETED_BG);
            }
            case "CANCELLED" -> {
                label.setForeground(AppColors.DANGER);
                label.setBackground(AppColors.STAT_CANCELLED_BODY);
            }
            default -> {
                label.setForeground(AppColors.LABEL);
                label.setBackground(AppColors.STAT_TOTAL_BODY);
            }
        }
    }

    private void applyStatusStyle(String status) {
        applyStatusStyle(this, status);
        setOpaque(false);
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
