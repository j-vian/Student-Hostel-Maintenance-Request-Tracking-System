package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public class PasswordFieldPanel extends JPanel {

    private final PlaceholderPasswordField passwordField;

    /** No-arg constructor required by the NetBeans Form Editor at design time. */
    public PasswordFieldPanel() {
        this("Placeholder");
    }

    public PasswordFieldPanel(String placeholder) {
        this(placeholder, UIHelper.FIELD_WIDTH);
    }

    public PasswordFieldPanel(String placeholder, int width) {
        passwordField = new PlaceholderPasswordField(placeholder);
        JPanel container = UIHelper.createPasswordWithToggle(passwordField, width);
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
