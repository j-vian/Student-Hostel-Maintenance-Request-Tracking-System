package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class PriorityBadgeLabel extends JLabel {

    /** No-arg constructor required by the NetBeans Form Editor at design time. */
    public PriorityBadgeLabel() {
        this("Medium");
    }

    public PriorityBadgeLabel(String priority) {
        super(formatPriority(priority));
        applyPriorityStyle(priority);
    }

    public static String formatPriority(String priority) {
        if (priority == null) {
            return "";
        }
        String normalized = priority.trim();
        if (normalized.isEmpty()) {
            return "";
        }
        return Character.toUpperCase(normalized.charAt(0)) + normalized.substring(1).toLowerCase();
    }

    public static void applyPriorityStyle(JLabel label, String priority) {
        label.setText(formatPriority(priority));
        label.setFont(AppFonts.statusBadge());
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(4, 12, 4, 12));

        String normalized = formatPriority(priority);
        switch (normalized) {
            case "High" -> {
                label.setForeground(AppColors.DANGER);
                label.setBackground(AppColors.STAT_CANCELLED_BODY);
            }
            case "Medium" -> {
                label.setForeground(AppColors.WARNING);
                label.setBackground(AppColors.STATUS_IN_PROGRESS_BG);
            }
            case "Low" -> {
                label.setForeground(AppColors.MUTED);
                label.setBackground(AppColors.STAT_TOTAL_BODY);
            }
            default -> {
                label.setForeground(AppColors.LABEL);
                label.setBackground(AppColors.STAT_TOTAL_BODY);
            }
        }
    }

    private void applyPriorityStyle(String priority) {
        applyPriorityStyle(this, priority);
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
