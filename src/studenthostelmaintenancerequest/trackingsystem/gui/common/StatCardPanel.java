package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class StatCardPanel extends JPanel {

    // UI component and state fields
    private final JLabel lblTitle;
    private final JLabel lblCount;

    /** No-arg constructor required by the NetBeans Form Editor at design time. */
    // construct frame and initialize UI
    public StatCardPanel() {
        this("Total Requests", "0", AppColors.STAT_TOTAL_HEADER, AppColors.STAT_TOTAL_BODY);
    }

    // construct frame and initialize UI
    public StatCardPanel(String title, String count, Color headerBg, Color bodyBg) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(AppColors.GRID_LINE, 1));

        lblTitle = new JLabel(title, JLabel.CENTER);
        lblCount = new JLabel(count, JLabel.CENTER);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(true);
        header.setBackground(headerBg);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.GRID_LINE),
                new EmptyBorder(10, 12, 10, 12)));
        header.add(lblTitle, BorderLayout.CENTER);

        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(true);
        body.setBackground(bodyBg);
        body.setBorder(new EmptyBorder(20, 12, 24, 12));
        body.add(lblCount, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }

    // return requested value
    public JLabel getTitleLabel() {
        return lblTitle;
    }

    public JLabel getCountLabel() {
        return lblCount;
    }

    public void setCount(String count) {
        lblCount.setText(count);
    }
}
