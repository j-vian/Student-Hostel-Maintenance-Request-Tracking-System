package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public class PasswordRequirementsPanel extends JPanel {

    // construct frame and initialize UI
    public PasswordRequirementsPanel() {
        JPanel requirements = UIHelper.createRequirementsPanel();
        setOpaque(false);
        setLayout(new BorderLayout());
        add(requirements, BorderLayout.CENTER);
        Dimension size = requirements.getPreferredSize();
        setPreferredSize(new Dimension(UIHelper.FIELD_WIDTH, size.height));
        setMinimumSize(getPreferredSize());
        setMaximumSize(new Dimension(UIHelper.FIELD_WIDTH, size.height));
    }
}
