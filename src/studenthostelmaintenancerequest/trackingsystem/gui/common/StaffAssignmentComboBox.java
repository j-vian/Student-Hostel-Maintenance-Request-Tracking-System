package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class StaffAssignmentComboBox extends JComboBox<String> {

    // construct frame and initialize UI
    public StaffAssignmentComboBox() {
        this(new String[0]);
    }

    // construct frame and initialize UI
    public StaffAssignmentComboBox(String[] staffOptions) {
        super(staffOptions == null ? new String[0] : staffOptions);
        setFont(AppFonts.body());
        setBackground(AppColors.SURFACE);
        setBorder(new LineBorder(AppColors.GRID_LINE, 1, true));
        setRenderer(new StaffListCellRenderer());
    }

    // update object state
    public void setSelectedStaff(String staff) {
        if (staff == null || staff.isBlank()) {
            if (getItemCount() > 0) {
                setSelectedIndex(0);
            }
            return;
        }
        for (int i = 0; i < getItemCount(); i++) {
            String item = getItemAt(i);
            if (item.equals(staff) || item.startsWith(staff) || staff.startsWith(item.split(" \\(")[0])) {
                setSelectedIndex(i);
                return;
            }
        }
        if (getItemCount() > 0) {
            setSelectedIndex(0);
        }
    }

    public String getSelectedStaffName() {
        Object selected = getSelectedItem();
        return displayName(selected == null ? null : String.valueOf(selected));
    }

    public static String displayName(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        int roleStart = value.indexOf(" (");
        return roleStart > 0 ? value.substring(0, roleStart) : value;
    }

    private static final class StaffListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            if (value == null) {
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }

            boolean isClosedCombo = list == null || !list.isShowing();
            if (isClosedCombo) {
                return new StaffDropdownPanel(displayName(String.valueOf(value)), false, false);
            }

            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
            wrapper.setBackground(AppColors.SURFACE);
            JLabel label = new JLabel(String.valueOf(value));
            label.setFont(AppFonts.body());
            label.setForeground(AppColors.LABEL);
            wrapper.add(label);
            return wrapper;
        }
    }
}
