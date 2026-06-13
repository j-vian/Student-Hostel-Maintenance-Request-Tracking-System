package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ManagerRoomInformationPanel extends JPanel {

    private static final int CARD_HEIGHT = 300;
    private static final int ROW_HEIGHT = 118;

    private final JLabel lblRequestIdBadge;
    private final JLabel lblRoomNumber;
    private final JLabel lblBlock;
    private final JLabel lblStudentName;
    private final JLabel lblRequestType;

    public ManagerRoomInformationPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(AppColors.GRID_LINE, 1));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
        setPreferredSize(new Dimension(0, 0));
        setVisible(false);

        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(true);
        header.setBackground(AppColors.STAT_TOTAL_HEADER);
        header.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.GRID_LINE),
                new EmptyBorder(10, 14, 10, 14)));

        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        titleRow.setOpaque(false);
        titleRow.add(createInfoIcon());
        JLabel lblTitle = new JLabel("Room Information");
        lblTitle.setFont(AppFonts.tableSection());
        lblTitle.setForeground(AppColors.LABEL);
        titleRow.add(lblTitle);

        lblRequestIdBadge = new JLabel("ID: REQ001", JLabel.CENTER);
        lblRequestIdBadge.setFont(AppFonts.bodyBold());
        lblRequestIdBadge.setForeground(AppColors.LABEL);
        lblRequestIdBadge.setOpaque(true);
        lblRequestIdBadge.setBackground(AppColors.SURFACE);
        lblRequestIdBadge.setBorder(new CompoundBorder(
                new LineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(4, 12, 4, 12)));

        header.add(titleRow, BorderLayout.WEST);
        header.add(lblRequestIdBadge, BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(2, 2, 0, 0));
        grid.setOpaque(true);
        grid.setBackground(AppColors.SURFACE);
        grid.setPreferredSize(new Dimension(0, ROW_HEIGHT * 2));

        lblRoomNumber = createValueLabel();
        lblBlock = createValueLabel();
        lblStudentName = createValueLabel();
        lblRequestType = createValueLabel();

        grid.add(createDetailCell("ROOM NUMBER", lblRoomNumber, true, false));
        grid.add(createDetailCell("BLOCK", lblBlock, true, true));
        grid.add(createDetailCell("STUDENT NAME", lblStudentName, false, false));
        grid.add(createDetailCell("REQUEST TYPE", lblRequestType, false, true));

        add(header, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
    }

    public void setDetails(String requestId, String roomNumber, String block,
            String studentName, String requestType) {
        lblRequestIdBadge.setText("ID: " + requestId);
        lblRoomNumber.setText(roomNumber);
        lblBlock.setText(block);
        lblStudentName.setText(studentName);
        lblRequestType.setText(requestType);
        setPreferredSize(new Dimension(0, CARD_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, CARD_HEIGHT));
        setVisible(true);
    }

    public void clearDetails() {
        setVisible(false);
        setPreferredSize(new Dimension(0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
    }

    private static JLabel createInfoIcon() {
        JLabel icon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.LABEL);
                g2.drawOval(2, 2, getWidth() - 4, getHeight() - 4);
                g2.setFont(AppFonts.bodyBold().deriveFont(11f));
                int x = (getWidth() - g2.getFontMetrics().stringWidth("i")) / 2;
                int y = ((getHeight() - g2.getFontMetrics().getHeight()) / 2) + g2.getFontMetrics().getAscent();
                g2.drawString("i", x, y);
                g2.dispose();
            }
        };
        icon.setPreferredSize(new Dimension(18, 18));
        return icon;
    }

    private static JLabel createValueLabel() {
        JLabel label = new JLabel("", JLabel.LEFT);
        label.setFont(AppFonts.bodyBold().deriveFont(20f));
        label.setForeground(AppColors.LABEL);
        return label;
    }

    private static JPanel createDetailCell(
            String title, JLabel valueLabel, boolean topRow, boolean rightColumn) {

        JPanel cell = new JPanel(new BorderLayout(0, 10));
        cell.setOpaque(true);
        cell.setBackground(AppColors.SURFACE);
        cell.setBorder(new CompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, topRow ? 1 : 0, 0, AppColors.SURFACE),
                        BorderFactory.createMatteBorder(0, 0, 0, rightColumn ? 0 : 1, AppColors.GRID_LINE)),
                new EmptyBorder(22, 24, 22, 24)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(AppFonts.statTitle());
        lblTitle.setForeground(AppColors.MUTED);

        cell.add(lblTitle, BorderLayout.NORTH);
        cell.add(valueLabel, BorderLayout.CENTER);
        return cell;
    }
}
