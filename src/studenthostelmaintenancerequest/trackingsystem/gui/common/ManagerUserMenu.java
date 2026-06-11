package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

public final class ManagerUserMenu {

    private ManagerUserMenu() {
    }

    public static void install(
            JPanel trigger,
            JLabel lblUserName,
            JLabel lblUserRole,
            JButton chevron,
            Runnable onLogOut) {

        styleTrigger(trigger, lblUserName, lblUserRole, chevron);

        JPopupMenu menu = buildMenu(onLogOut);

        MouseAdapter opener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menu.show(trigger, trigger.getWidth() - menu.getPreferredSize().width, trigger.getHeight() + 6);
            }
        };

        trigger.addMouseListener(opener);
        chevron.addMouseListener(opener);
        lblUserName.addMouseListener(opener);
        lblUserRole.addMouseListener(opener);
    }

    private static void styleTrigger(JPanel trigger, JLabel lblUserName, JLabel lblUserRole, JButton chevron) {
        trigger.setOpaque(true);
        trigger.setBackground(AppColors.SURFACE);
        trigger.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(6, 12, 6, 8)));
        trigger.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblUserName.setFont(AppFonts.userName());
        lblUserName.setForeground(AppColors.LABEL);
        lblUserRole.setFont(AppFonts.userRole());
        lblUserRole.setForeground(AppColors.MUTED);

        chevron.setFont(AppFonts.body());
        chevron.setForeground(AppColors.MUTED);
        chevron.setText("\u25BE");
        chevron.setBorderPainted(false);
        chevron.setContentAreaFilled(false);
        chevron.setFocusPainted(false);
        chevron.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Dimension chevronSize = new Dimension(20, 20);
        chevron.setPreferredSize(chevronSize);
        chevron.setMinimumSize(chevronSize);
        chevron.setMaximumSize(chevronSize);

        trigger.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                trigger.setBackground(AppColors.STAT_TOTAL_BODY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                trigger.setBackground(AppColors.SURFACE);
            }
        });
    }

    private static JPopupMenu buildMenu(Runnable onLogOut) {
        JPopupMenu menu = new JPopupMenu();
        menu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(4, 4, 4, 4)));

        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBackground(AppColors.SURFACE);
        panel.setMinimumSize(new Dimension(140, 0));

        JButton logOut = new JButton("Log Out") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(AppColors.STAT_CANCELLED_BODY);
                    g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logOut.setFont(AppFonts.bodyBold());
        logOut.setForeground(AppColors.DANGER);
        logOut.setHorizontalAlignment(JButton.CENTER);
        logOut.setBorderPainted(false);
        logOut.setContentAreaFilled(false);
        logOut.setFocusPainted(false);
        logOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logOut.setBorder(new EmptyBorder(10, 20, 10, 20));
        logOut.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        logOut.addActionListener(e -> {
            menu.setVisible(false);
            onLogOut.run();
        });

        panel.add(logOut);
        menu.add(panel);
        return menu;
    }
}
