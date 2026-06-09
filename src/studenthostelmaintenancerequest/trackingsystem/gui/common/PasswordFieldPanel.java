package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public class PasswordFieldPanel extends JPanel {

    private final PlaceholderPasswordField passwordField;

    public PasswordFieldPanel(String placeholder) {
        passwordField = new PlaceholderPasswordField(placeholder);
        JPanel container = UIHelper.createPasswordWithToggle(passwordField);
        setOpaque(false);
        setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);
        Dimension size = container.getPreferredSize();
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public PlaceholderPasswordField getPasswordField() {
        return passwordField;
    }
}
