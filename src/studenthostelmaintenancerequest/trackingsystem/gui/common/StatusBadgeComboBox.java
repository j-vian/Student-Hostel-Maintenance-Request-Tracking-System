package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;

public class StatusBadgeComboBox extends JComboBox<String> {

    private static final String[] MANAGER_STATUS_OPTIONS = {"IN PROGRESS", "COMPLETED", "CANCELLED"};

    public StatusBadgeComboBox() {
        super(MANAGER_STATUS_OPTIONS);
        setFont(AppFonts.statusBadge());
        setBackground(AppColors.SURFACE);
        setBorder(null);
        setRenderer(new StatusBadgeListCellRenderer());
    }

    public void setSelectedStatus(String status) {
        String normalized = StatusBadgeLabel.formatStatus(status);
        for (int i = 0; i < getItemCount(); i++) {
            if (normalized.equals(getItemAt(i))) {
                setSelectedIndex(i);
                return;
            }
        }
        if ("SUBMITTED".equals(normalized)) {
            setSelectedIndex(0);
        } else {
            setSelectedItem(normalized);
        }
    }

    private static final class StatusBadgeListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
            wrapper.setBackground(AppColors.SURFACE);
            if (value != null) {
                wrapper.add(new StatusBadgeLabel(String.valueOf(value)));
            }
            return wrapper;
        }
    }
}
